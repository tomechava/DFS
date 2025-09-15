// NameNodeServer.h
#pragma once

#include <grpcpp/grpcpp.h>
#include "dfs.grpc.pb.h"
#include "MetadataManager.h"

// Nota: MetadataManager::BlockLocation (definido en MetadataManager.h)
// es un tipo distinto a dfs::BlockLocation (generado por protoc).
// Aquí usamos dfs::* para los mensajes gRPC y MetadataManager para la tabla.

class NameNodeServiceImpl final : public dfs::NameNodeService::Service {
public:
    // metadata: puntero al gestor de metadatos (vital que viva mientras el servicio exista)
    explicit NameNodeServiceImpl(MetadataManager* metadata);

    grpc::Status PutFile(grpc::ServerContext* context,
                         const dfs::PutFileRequest* request,
                         dfs::PutFileResponse* response) override;

    grpc::Status GetFile(grpc::ServerContext* context,
                         const dfs::GetFileRequest* request,
                         dfs::GetFileResponse* response) override;

    grpc::Status ListFiles(grpc::ServerContext* context,
                           const dfs::ListFilesRequest* request,
                           dfs::ListFilesResponse* response) override;

    grpc::Status RemoveFile(grpc::ServerContext* context,
                            const dfs::RemoveFileRequest* request,
                            dfs::RemoveFileResponse* response) override;
    
    grpc::Status GetReplicas(grpc::ServerContext* context,
                             const dfs::ReplicaRequest* request,
                             dfs::ReplicaResponse* response) override;

    grpc::Status Mkdir(grpc::ServerContext* context,
                       const dfs::MkdirRequest* request,
                       dfs::MkdirResponse* response) override;

    grpc::Status Rmdir(grpc::ServerContext* context,
                       const dfs::RmdirRequest* request,
                       dfs::RmdirResponse* response) override;
private:
    MetadataManager* metadata;
};
