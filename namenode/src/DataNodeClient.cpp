#include "DataNodeClient.h"
#include <iostream>

namespace dfs {

// ------------------ DataNodeService ------------------

bool DataNodeClient::UploadBlock(int32_t block_id, const std::string& filename, const std::string& data, std::string* err_msg) {
    BlockUploadRequest req;
    req.set_block_id(block_id);
    req.set_filename(filename);
    req.set_data(data); // bytes

    BlockUploadResponse resp;
    grpc::ClientContext ctx;

    grpc::Status status = dn_stub_->UploadBlock(&ctx, req, &resp);
    if (!status.ok()) {
        if (err_msg) *err_msg = status.error_message();
        return false;
    }
    return resp.success();
}

bool DataNodeClient::DownloadBlock(int32_t block_id, const std::string& filename, std::string& out_data, std::string* err_msg) {
    BlockDownloadRequest req;
    req.set_block_id(block_id);
    req.set_filename(filename);

    BlockDownloadResponse resp;
    grpc::ClientContext ctx;

    grpc::Status status = dn_stub_->DownloadBlock(&ctx, req, &resp);
    if (!status.ok()) {
        if (err_msg) *err_msg = status.error_message();
        return false;
    }
    out_data = resp.data(); // bytes
    return true;
}

// ------------------ ClusterService ------------------

bool DataNodeClient::RegisterDataNode(const std::string& datanode_id, const std::string& ip, int port, std::string* err_msg) {
    RegisterDataNodeRequest req;
    req.set_datanode_id(datanode_id);
    req.set_ip_address(ip);
    req.set_port(port);

    RegisterDataNodeResponse resp;
    grpc::ClientContext ctx;

    grpc::Status status = cluster_stub_->RegisterDataNode(&ctx, req, &resp);
    if (!status.ok()) {
        if (err_msg) *err_msg = status.error_message();
        return false;
    }
    return resp.success();
}

bool DataNodeClient::Heartbeat(const std::string& datanode_id, std::string* err_msg) {
    HeartbeatRequest req;
    req.set_datanode_id(datanode_id);

    HeartbeatResponse resp;
    grpc::ClientContext ctx;

    grpc::Status status = cluster_stub_->Heartbeat(&ctx, req, &resp);
    if (!status.ok()) {
        if (err_msg) *err_msg = status.error_message();
        return false;
    }
    return resp.success();
}

bool DataNodeClient::ReportBlock(const std::string& datanode_id, int32_t block_id, const std::string& filename, std::string* err_msg) {
    ReportBlockRequest req;
    req.set_datanode_id(datanode_id);
    auto* br = req.add_blocks();
    br->set_block_id(block_id);
    br->set_filename(filename);

    ReportBlockResponse resp;
    grpc::ClientContext ctx;

    grpc::Status status = cluster_stub_->ReportBlock(&ctx, req, &resp);
    if (!status.ok()) {
        if (err_msg) *err_msg = status.error_message();
        return false;
    }
    return resp.success();
}

} // namespace dfs
