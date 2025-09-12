#include "ClusterServiceImpl.h"
#include <iostream>

using grpc::Status;
using grpc::ServerContext;

ClusterServiceImpl::ClusterServiceImpl(MetadataManager* metadata)
    : metadata(metadata) {}

Status ClusterServiceImpl::RegisterDataNode(ServerContext* context,
                                           const dfs::RegisterDataNodeRequest* request,
                                           dfs::RegisterDataNodeResponse* response) {
    std::string id = request->datanode_id();
    std::string address = request->ip_address() + ":" + std::to_string(request->port());

    metadata->registerDataNode(id, address);

    std::cout << "DataNode registrado: " << id
              << " en " << address << std::endl;

    response->set_success(true);
    return Status::OK;
}

Status ClusterServiceImpl::Heartbeat(ServerContext* context,
                                    const dfs::HeartbeatRequest* request,
                                    dfs::HeartbeatResponse* response) {
    std::string id = request->datanode_id();

    // 👇 por ahora sólo marcamos como vivo sin manejar last_heartbeat
    auto aliveNodes = metadata->getAliveDataNodes();
    bool exists = std::find(aliveNodes.begin(), aliveNodes.end(), id) != aliveNodes.end();

    if (!exists) {
        std::cerr << "Heartbeat de nodo no registrado: " << id << std::endl;
        response->set_success(false);
        return Status::CANCELLED;
    }

    std::cout << "Heartbeat recibido de " << id << std::endl;
    response->set_success(true);
    return Status::OK;
}

Status ClusterServiceImpl::ReportBlock(ServerContext* context,
                                       const dfs::ReportBlockRequest* request,
                                       dfs::ReportBlockResponse* response) {
    std::string datanodeId = request->datanode_id();

    for (const auto& block : request->blocks()) {
        int64_t blockId = block.block_id();

        // En este punto lo único que podemos hacer es añadir réplicas al mapa
        auto replicas = metadata->getReplicasForBlock(blockId);
        replicas.push_back(datanodeId);
        metadata->registerBlockReplicas(blockId, replicas);

        std::cout << "📦 DataNode " << datanodeId
                  << " reporta bloque " << blockId << std::endl;
    }

    response->set_success(true);
    return Status::OK;
}
