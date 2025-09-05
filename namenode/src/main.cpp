#include <iostream>
#include <memory>
#include <string>
#include <grpcpp/grpcpp.h>
#include "dfs.grpc.pb.h"
#include "DataNodeClient.h"

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
    std::string server_address("0.0.0.0:50052");
    dfs::NameNodeServiceImpl service;

    ServerBuilder builder;
    builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
    builder.RegisterService(&service);

    std::unique_ptr<Server> server(builder.BuildAndStart());
    std::cout << "NameNode gRPC server running on " << server_address << std::endl;

    dfs::DataNodeClient dn("127.0.0.1:50051");

    // 2.1 Prueba UploadBlock
    std::string err;
    bool ok = dn.UploadBlock(/*block_id*/ 1, /*filename*/ "hello.txt", /*data*/ "Hola desde NameNode C++", &err);
    std::cout << "[NameNode->DataNode] UploadBlock: " << (ok ? "OK" : ("FAIL: " + err)) << std::endl;

    // 2.2 Prueba DownloadBlock
    std::string downloaded;
    ok = dn.DownloadBlock(1, "hello.txt", downloaded, &err);
    std::cout << "[NameNode->DataNode] DownloadBlock: " << (ok ? "OK" : ("FAIL: " + err)) << std::endl;
    if (ok) std::cout << "Contenido: " << downloaded << std::endl;

    // 2.3 (Opcional) Registro/heartbeat
    // dn.RegisterDataNode("dn-1", "127.0.0.1", 50052, &err);
    // dn.Heartbeat("dn-1", &err);
}

int main() {
    RunServer();
    return 0;
}
