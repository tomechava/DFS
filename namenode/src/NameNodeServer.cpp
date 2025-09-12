#include "NameNodeServer.h"
#include "PartitionUtils.h"
#include "HashUtils.h"
#include <iostream>

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

    // Elegir block_size dinámicamente
    uint64_t blockSize = 64 * 1024 * 1024;
    uint64_t numBlocks = (filesize + blockSize - 1) / blockSize;
    std::cout << "Archivo dividido en " << numBlocks
              << " bloques de " << blockSize << " bytes" << std::endl;

    auto availableNodes = metadata->getAliveDataNodes();
    if (availableNodes.empty()) {
        std::cerr << "No hay DataNodes registrados." << std::endl;
        return Status::CANCELLED;
    }

    for (uint64_t i = 0; i < numBlocks; i++) {
        // ID único usando hash
        std::string key = filename + ":" + std::to_string(i);
        int64_t block_id = static_cast<int64_t>(dfs::HashUtils::hash64(key));


        // Usamos PartitionUtils para decidir primario + réplicas
        auto assignment = dfs::PartitionUtils::assignBlockNodes(
            filename, block_id, availableNodes, 3 // replication factor
        );

        std::string primary = assignment.first;
        const auto& replicas = assignment.second;

        dfs::BlockLocation* block = response->add_blocks();
        block->set_block_id(block_id);
        block->set_primary_address(primary);
        for (const auto& replica : replicas) {
            block->add_replica_addresses(replica);
        }

        // Guardar en metadata
        metadata->registerBlockLocation(filename, {std::to_string(block_id), primary});

        std::vector<std::string> all = {primary};
        all.insert(all.end(), replicas.begin(), replicas.end());
        metadata->registerBlockReplicas(block_id, all);
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

    std::cout << "Réplicas registradas: ";
    for (const auto& n : replicas) std::cout << n << " ";
    std::cout << std::endl;

    // omitimos el primario, devolvemos solo secundarias
    for (size_t i = 1; i < replicas.size(); i++) {
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

        // block_id lo guardaste como string, conviértelo a int64
        int64_t blockId = std::stoll(b.block_id);
        blockLoc->set_block_id(blockId);

        // primario (lo que guardaste como datanode_address)
        blockLoc->set_primary_address(b.datanode_address);

        // ahora réplicas desde MetadataManager
        auto replicas = metadata->getReplicasForBlock(blockId);
        for (size_t i = 1; i < replicas.size(); i++) {
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