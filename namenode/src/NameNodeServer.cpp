#include "NameNodeServer.h"
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
    std::cout << "Cliente sube archivo: " << request->filename()
              << " (" << request->filesize() << " bytes)" << std::endl;

    // Dividir en bloques (ejemplo: 64 MB por bloque, aquí simplificado)
    int blockSize = 64 * 1024 * 1024;
    std::cout << "Tamano del archivo recibido: " << request->filesize() << " bytes" << std::endl;
    int numBlocks = static_cast<int>((request->filesize() + blockSize - 1) / blockSize);
    std::cout << "Archivo dividido en " << numBlocks << " bloques." << std::endl;

    for (int i = 0; i < numBlocks; i++) {
        dfs::BlockLocation* block = response->add_blocks();
        block->set_block_id(i);

        // Asignar DataNode (simulado fijo en localhost:50051)
        block->set_datanode_address("127.0.0.1:50051");

        // Guardar metadatos en la tabla
        BlockLocation metaBlock{std::to_string(i), block->datanode_address()};
        metadata->registerBlockLocation(request->filename(), metaBlock);
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
        dfs::BlockLocation* block = response->add_blocks();
        block->set_block_id(std::stoi(b.block_id));
        block->set_datanode_address(b.datanode_address);
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
