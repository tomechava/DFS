#include "NameNodeServer.h"
#include "PartitionUtils.h"
#include "HashUtils.h"
#include <iostream>
#include <algorithm> // std::min
#include <vector>
#include <cstdint>

using grpc::Status;
using grpc::ServerContext;

NameNodeServiceImpl::NameNodeServiceImpl(MetadataManager* metadata)
    : metadata(metadata) {}

// ==============================================
// Subida de archivo (Cliente -> NameNode)
// ==============================================
Status NameNodeServiceImpl::PutFile(ServerContext* context,
                                    const dfs::PutFileRequest* request,
                                    dfs::PutFileResponse* response) {
    uint64_t filesize = request->filesize();
    std::string filename = request->filename();
    std::cout << "Cliente sube archivo: " << filename
              << " (" << filesize << " bytes)" << std::endl;

    // Tamaño de bloque (puedes hacerlo dinámico con choose_block_size si lo implementas)
    uint64_t blockSize = 64ull * 1024ull * 1024ull;
    uint64_t numBlocks = (filesize + blockSize - 1) / blockSize;
    std::cout << "Archivo dividido en " << numBlocks
              << " bloques de " << blockSize << " bytes" << std::endl;

    // Obtener nodos vivos desde metadata
    std::vector<std::string> allNodes = metadata->getAliveDataNodes();
    if (allNodes.empty()) {
        std::cerr << "❌ No hay DataNodes registrados." << std::endl;
        return Status::CANCELLED;
    }

    // Factor de replicación (configurable)
    int replicationFactor = 3;

    for (uint64_t i = 0; i < numBlocks; ++i) {
        
        uint64_t block_id = dfs::HashUtils::blockIdFor(filename, i);
        std::cout << "block_id (i=" << i << ") = " << block_id << std::endl;
        // Ajustar replicationFactor al máximo disponible
        int effectiveReplication = std::min(replicationFactor, static_cast<int>(allNodes.size()));

        // Asignación: primario + réplicas (PartitionUtils ya evita duplicados)
        auto assignment = dfs::PartitionUtils::assignBlockNodes(filename, block_id, allNodes, effectiveReplication);
        std::cout << "Asignación bloque " << block_id
             << ": primario " << assignment.first
             << ", réplicas [";
        for (const auto& r : assignment.second) {
            std::cout << r << " ";
        }
        std::cout << "]" << std::endl;
        std::string primary = assignment.first;
        const std::vector<std::string>& replicas = assignment.second;

        // Construir respuesta para el cliente (solo primary + lista opcional de réplicas)
        dfs::BlockLocation* block = response->add_blocks();
        block->set_block_id(static_cast<int64_t>(block_id));
        block->set_primary_address(primary);
        for (const auto& r : replicas) {
            block->add_replica_addresses(r);
        }

        // Construir un objeto BlockLocation (nuestro propio struct de metadatos)
        BlockLocation metaBlock;
        metaBlock.block_id = std::to_string(block->block_id());   // se guarda como string
        metaBlock.datanode_address = block->primary_address();    // primario

        // Guardar en metadatos
        std::cout << "➡ Guardando bloque en metadatos" << std::endl;
        metadata->registerBlockLocation(filename, metaBlock);
        std::cout << "✅ Guardado bloque en metadatos" << std::endl;


        // Guardar réplicas en tabla separada
        std::vector<std::string> all;
        all.push_back(primary);
        all.insert(all.end(), replicas.begin(), replicas.end());
        metadata->registerBlockReplicas(block_id, all);
        std::cout << "✅ Guardadas réplicas en metadatos" << std::endl;
        std::cout << "Asignación bloque " << block_id
             << ": primario " << assignment.first
             << ", réplicas [";
        for (const auto& r : assignment.second) {
            std::cout << r << " ";
        }
        std::cout << "]" << std::endl;
        std::cout << "Iteración " << i << " completada" << std::endl;

    }

    return Status::OK;
}

// ==============================================
// Consulta de réplicas (DataNode -> NameNode)
// ==============================================
Status NameNodeServiceImpl::GetReplicas(ServerContext* context,
                                        const dfs::ReplicaRequest* request,
                                        dfs::ReplicaResponse* response) {
    int64_t blockId = request->block_id();
    std::cout << "DataNode solicita réplicas para bloque ID " << blockId << std::endl;

    auto replicas = metadata->getReplicasForBlock(blockId);
    if (replicas.empty()) {
        std::cerr << "No se encontró el bloque " << blockId << " en metadatos." << std::endl;
        return Status::CANCELLED;
    }

    // replicas[0] se considera primario (según nuestro contrato); devolvemos solo las secundarias
    for (size_t i = 1; i < replicas.size(); ++i) {
        response->add_replica_datanodes(replicas[i]);
    }

    return Status::OK;
}

// ==============================================
// Descarga de archivo (Cliente -> NameNode)
// ==============================================
Status NameNodeServiceImpl::GetFile(ServerContext* context,
                                    const dfs::GetFileRequest* request,
                                    dfs::GetFileResponse* response) {
    std::cout << "Cliente solicita archivo: " << request->filename() << std::endl;

    auto blocks = metadata->lookupFileBlocks(request->filename());
    for (const auto& b : blocks) {
        dfs::BlockLocation* blockLoc = response->add_blocks();

        // block_id lo guardamos como string en metadatos, aquí lo convertimos
        int64_t blockId = 0;
        try {
            blockId = std::stoll(b.block_id);
        } catch (...) {
            std::cerr << "Aviso: block id corrupto en metadatos: " << b.block_id << std::endl;
            continue;
        }
        blockLoc->set_block_id(blockId);

        // primario
        blockLoc->set_primary_address(b.datanode_address);

        // añadir réplicas (si existen)
        auto replicas = metadata->getReplicasForBlock(blockId);
        for (size_t i = 1; i < replicas.size(); ++i) {
            blockLoc->add_replica_addresses(replicas[i]);
        }
    }

    return Status::OK;
}

// ==============================================
// Listar archivos (Cliente -> NameNode)
// ==============================================
Status NameNodeServiceImpl::ListFiles(ServerContext* context,
                                      const dfs::ListFilesRequest* request,
                                      dfs::ListFilesResponse* response) {
    std::cout << "Cliente lista archivos de usuario: " << request->username() << std::endl;

    auto files = metadata->listFilesForUser(request->username());
    for (const auto& f : files) {
        response->add_filenames(f);
    }

    return Status::OK;
}

// ==============================================
// Eliminar archivo (Cliente -> NameNode)
// ==============================================
Status NameNodeServiceImpl::RemoveFile(ServerContext* context,
                                       const dfs::RemoveFileRequest* request,
                                       dfs::RemoveFileResponse* response) {
    std::cout << "Cliente elimina archivo: " << request->filename() << std::endl;

    metadata->unregisterFile(request->filename());
    response->set_success(true);
    response->set_message("Archivo eliminado de metadatos");

    return Status::OK;
}

Status NameNodeServiceImpl::Mkdir(ServerContext* context,
                                  const dfs::MkdirRequest* request,
                                  dfs::MkdirResponse* response) {
    std::string username = request->username();
    std::string path = request->path();

    std::cout << "[NameNode] Usuario " << username << " pide mkdir: " << path << std::endl;

    // Aquí podrías validar permisos, existencia en metadata, etc.
    response->set_success(true);
    response->set_message("Directorio autorizado por NameNode");

    return Status::OK;
}

Status NameNodeServiceImpl::Rmdir(ServerContext* context,
                                  const dfs::RmdirRequest* request,
                                  dfs::RmdirResponse* response) {
    std::string username = request->username();
    std::string path = request->path();

    std::cout << "[NameNode] Usuario " << username << " pide rmdir: " << path << std::endl;

    response->set_success(true);
    response->set_message("Eliminación autorizada por NameNode");

    return Status::OK;
}
