#include "ClusterServiceImpl.h"
#include <iostream>
#include <algorithm>

using grpc::Status;
using grpc::ServerContext;

ClusterServiceImpl::ClusterServiceImpl(MetadataManager* metadata)
    : metadata(metadata) {}

Status ClusterServiceImpl::RegisterDataNode(ServerContext* context,
                                           const dfs::RegisterDataNodeRequest* request,
                                           dfs::RegisterDataNodeResponse* response) {
    std::string id = request->datanode_id();
    std::string ip = request->ip_address();
    int port = request->port();

    // ✅ Pasamos id, ip y puerto separados
    metadata->registerDataNode(id, ip, port);

    std::cout << "✅ DataNode registrado: " << id
              << " @ " << ip << ":" << port << std::endl;

    response->set_success(true);
    return Status::OK;
}

Status ClusterServiceImpl::Heartbeat(ServerContext* context,
                                    const dfs::HeartbeatRequest* request,
                                    dfs::HeartbeatResponse* response) {
    std::string id = request->datanode_id();
    std::string ip = request->ip_address();
    int port = request->port();

    metadata->updateHeartbeat(id, ip, port); //Necesitas implementar updateHeartbeat en MetadataManager

    std::cout << "Heartbeat recibido de " << id
              << " @ " << ip << ":" << port << std::endl;

    response->set_success(true);
    return Status::OK;
}


Status ClusterServiceImpl::ReportBlock(ServerContext* context,
                                       const dfs::ReportBlockRequest* request,
                                       dfs::ReportBlockResponse* response) {
    std::string datanodeId = request->datanode_id();
    std::string ip = request->ip_address();
    int port = request->port();
    std::string address = ip + ":" + std::to_string(port);

    for (const auto& block : request->blocks()) {
        int64_t blockId = block.block_id();

        // Agregamos dirección real como réplica
        auto replicas = metadata->getReplicasForBlock(blockId);
        if (std::find(replicas.begin(), replicas.end(), address) == replicas.end()) {
            replicas.push_back(address);
            metadata->registerBlockReplicas(blockId, replicas);
        }

        std::cout << "📦 DataNode " << datanodeId
                  << " @ " << address
                  << " reporta bloque " << blockId << std::endl;
    }

    response->set_success(true);
    return Status::OK;
}
