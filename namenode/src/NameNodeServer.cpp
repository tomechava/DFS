#include "NameNodeServer.h"
#include "PartitionUtils.h"
#include "HashUtils.h"
#include <iostream>
#include <algorithm>
#include <vector>
#include <cstdint>

using grpc::Status;
using grpc::ServerContext;

NameNodeServiceImpl::NameNodeServiceImpl(MetadataManager* metadata)
    : metadata(metadata) {}

// ----------------------------------------------
// Helper: validación de token
// ----------------------------------------------
bool NameNodeServiceImpl::checkAuth(const std::string& username,
                                    const std::string& token,
                                    std::string* errorMsg) {
    if (!metadata->validateToken(username, token)) {
        if (errorMsg) *errorMsg = "Token inválido o sesión expirada";
        return false;
    }
    return true;
}

// ==============================================
// Login
// ==============================================
Status NameNodeServiceImpl::Login(ServerContext* context,
                                  const dfs::LoginRequest* request,
                                  dfs::LoginResponse* response) {
    if (metadata->validateUser(request->username(), request->password())) {
        std::string token = metadata->createSession(request->username());
        response->set_success(true);
        response->set_message("Login correcto");
        response->set_session_token(token);
    } else {
        response->set_success(false);
        response->set_message("Usuario o contraseña inválidos");
    }
    return Status::OK;
}

// ==============================================
// Subida de archivo
// ==============================================
Status NameNodeServiceImpl::PutFile(ServerContext* context,
                                    const dfs::PutFileRequest* request,
                                    dfs::PutFileResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        std::cerr << "❌ PutFile denegado: " << err << std::endl;
        return Status::OK; // devolvemos vacío
    }

    uint64_t filesize = request->filesize();
    std::string filename = request->filename();
    std::cout << "Cliente sube archivo: " << filename
              << " (" << filesize << " bytes)" << std::endl;

    uint64_t blockSize = 64ull * 1024ull * 1024ull;
    uint64_t numBlocks = (filesize + blockSize - 1) / blockSize;

    auto allNodes = metadata->getAliveDataNodes();
    if (allNodes.empty()) {
        std::cerr << "❌ No hay DataNodes registrados." << std::endl;
        return Status::CANCELLED;
    }

    int replicationFactor = 3;
    for (uint64_t i = 0; i < numBlocks; ++i) {
        uint64_t block_id = dfs::HashUtils::blockIdFor(filename, i);

        int effectiveReplication = std::min(replicationFactor, (int)allNodes.size());
        auto assignment = dfs::PartitionUtils::assignBlockNodes(filename, block_id, allNodes, effectiveReplication);

        dfs::BlockLocation* block = response->add_blocks();
        block->set_block_id((int64_t)block_id);
        block->set_primary_address(assignment.first);
        for (auto& r : assignment.second) {
            block->add_replica_addresses(r);
        }

        BlockLocation metaBlock;
        metaBlock.block_id = std::to_string(block->block_id());
        metaBlock.datanode_address = block->primary_address();
        metadata->registerBlockLocation(filename, metaBlock);

        std::vector<std::string> all;
        all.push_back(assignment.first);
        all.insert(all.end(), assignment.second.begin(), assignment.second.end());
        metadata->registerBlockReplicas(block_id, all);
    }

    return Status::OK;
}

// ==============================================
// Consulta de réplicas
// ==============================================
Status NameNodeServiceImpl::GetReplicas(ServerContext* context,
                                        const dfs::ReplicaRequest* request,
                                        dfs::ReplicaResponse* response) {
    int64_t blockId = request->block_id();
    auto replicas = metadata->getReplicasForBlock(blockId);
    for (size_t i = 1; i < replicas.size(); ++i) {
        response->add_replica_datanodes(replicas[i]);
    }
    return Status::OK;
}

// ==============================================
// Descarga de archivo
// ==============================================
Status NameNodeServiceImpl::GetFile(ServerContext* context,
                                    const dfs::GetFileRequest* request,
                                    dfs::GetFileResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        std::cerr << "❌ GetFile denegado: " << err << std::endl;
        return Status::OK;
    }

    auto blocks = metadata->lookupFileBlocks(request->filename());
    for (auto& b : blocks) {
        dfs::BlockLocation* blockLoc = response->add_blocks();
        int64_t blockId = 0;
        try { blockId = std::stoll(b.block_id); } catch (...) { continue; }
        blockLoc->set_block_id(blockId);
        blockLoc->set_primary_address(b.datanode_address);

        auto replicas = metadata->getReplicasForBlock(blockId);
        for (size_t i = 1; i < replicas.size(); ++i) {
            blockLoc->add_replica_addresses(replicas[i]);
        }
    }
    return Status::OK;
}

// ==============================================
// Listar archivos
// ==============================================
Status NameNodeServiceImpl::ListFiles(ServerContext* context,
                                      const dfs::ListFilesRequest* request,
                                      dfs::ListFilesResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        std::cerr << "❌ ListFiles denegado: " << err << std::endl;
        return Status::OK;
    }

    auto files = metadata->listFilesForUser(request->username());
    for (auto& f : files) response->add_filenames(f);
    return Status::OK;
}

// ==============================================
// Eliminar archivo
// ==============================================
Status NameNodeServiceImpl::RemoveFile(ServerContext* context,
                                       const dfs::RemoveFileRequest* request,
                                       dfs::RemoveFileResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        response->set_success(false);
        response->set_message(err);
        return Status::OK;
    }

    metadata->unregisterFile(request->filename());
    response->set_success(true);
    response->set_message("Archivo eliminado de metadatos");
    return Status::OK;
}

// ==============================================
// Crear directorio
// ==============================================
Status NameNodeServiceImpl::Mkdir(ServerContext* context,
                                  const dfs::MkdirRequest* request,
                                  dfs::MkdirResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        response->set_success(false);
        response->set_message(err);
        return Status::OK;
    }

    response->set_success(true);
    response->set_message("Directorio autorizado por NameNode");
    return Status::OK;
}

// ==============================================
// Eliminar directorio
// ==============================================
Status NameNodeServiceImpl::Rmdir(ServerContext* context,
                                  const dfs::RmdirRequest* request,
                                  dfs::RmdirResponse* response) {
    std::string err;
    if (!checkAuth(request->username(), request->session_token(), &err)) {
        response->set_success(false);
        response->set_message(err);
        return Status::OK;
    }

    response->set_success(true);
    response->set_message("Eliminación autorizada por NameNode");
    return Status::OK;
}
