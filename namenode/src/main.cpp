#include <iostream>
#include <memory>
#include <string>
#include <grpcpp/grpcpp.h>
#include "dfs.grpc.pb.h"
#include "NameNodeServer.h"
#include "MetadataManager.h"
#include "ClusterServiceImpl.h"
#include "HashUtils.h"
#include "PartitionUtils.h"

using grpc::Server;
using grpc::ServerBuilder;

void RunServer() {
    std::string server_address("0.0.0.0:50052");

    MetadataManager metadata;
    NameNodeServiceImpl nameNodeService(&metadata);
    ClusterServiceImpl clusterService(&metadata);  // <- ahora lo conectamos con metadata

    ServerBuilder builder;
    builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
    builder.RegisterService(&nameNodeService);
    builder.RegisterService(&clusterService);   // <- registramos cluster service

    std::unique_ptr<Server> server(builder.BuildAndStart());
    std::cout << "NameNode gRPC server running on " << server_address << std::endl;

    server->Wait();
}

int main() {
    RunServer();
    return 0;
}
