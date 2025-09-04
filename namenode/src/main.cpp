#include <iostream>
#include <memory>
#include <string>
#include <grpcpp/grpcpp.h>
#include "dfs.grpc.pb.h"

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;

namespace dfs {

// Implementación mínima del servicio NameNode
class NameNodeServiceImpl final : public NameNodeService::Service {
    Status PutFile(ServerContext* context,
                   const PutFileRequest* request,
                   PutFileResponse* response) override {
        std::cout << "Cliente sube archivo: " << request->filename() << std::endl;
        // Respuesta vacía solo para probar
        return Status::OK;
    }

    Status GetFile(ServerContext* context,
                   const GetFileRequest* request,
                   GetFileResponse* response) override {
        std::cout << "Cliente pide archivo: " << request->filename() << std::endl;
        return Status::OK;
    }

    Status ListFiles(ServerContext* context,
                     const ListFilesRequest* request,
                     ListFilesResponse* response) override {
        std::cout << "Cliente lista archivos de: " << request->username() << std::endl;
        return Status::OK;
    }

    Status RemoveFile(ServerContext* context,
                      const RemoveFileRequest* request,
                      RemoveFileResponse* response) override {
        std::cout << "Cliente elimina archivo: " << request->filename() << std::endl;
        response->set_success(true);
        response->set_message("Archivo eliminado (simulado)");
        return Status::OK;
    }
};

} // namespace dfs

void RunServer() {
    std::string server_address("0.0.0.0:50051");
    dfs::NameNodeServiceImpl service;

    ServerBuilder builder;
    builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
    builder.RegisterService(&service);

    std::unique_ptr<Server> server(builder.BuildAndStart());
    std::cout << "NameNode gRPC server running on " << server_address << std::endl;

    server->Wait();
}

int main() {
    RunServer();
    return 0;
}
