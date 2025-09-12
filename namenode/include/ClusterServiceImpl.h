#pragma once
#include "dfs.grpc.pb.h"
#include "MetadataManager.h"
#include "HashUtils.h"
#include "PartitionUtils.h"

class ClusterServiceImpl final : public dfs::ClusterService::Service {
public:
    explicit ClusterServiceImpl(MetadataManager* metadata);

    grpc::Status RegisterDataNode(grpc::ServerContext* context,
                                  const dfs::RegisterDataNodeRequest* request,
                                  dfs::RegisterDataNodeResponse* response) override;

    grpc::Status Heartbeat(grpc::ServerContext* context,
                           const dfs::HeartbeatRequest* request,
                           dfs::HeartbeatResponse* response) override;

    grpc::Status ReportBlock(grpc::ServerContext* context,
                             const dfs::ReportBlockRequest* request,
                             dfs::ReportBlockResponse* response) override;

private:
    MetadataManager* metadata;
};
