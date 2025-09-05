#pragma once
#include <memory>
#include <string>
#include <vector>
#include <grpcpp/grpcpp.h>
#include "dfs.grpc.pb.h"   // generado desde tu proto
#include "dfs.pb.h"

namespace dfs {

// Cliente para DataNodeService (upload/download de bloques)
// y ClusterService (register/heartbeat/report).
class DataNodeClient {
public:
    // address ejemplo: "127.0.0.1:50051"
    explicit DataNodeClient(const std::string& address)
        : channel_(grpc::CreateChannel(address, grpc::InsecureChannelCredentials())),
          dn_stub_(DataNodeService::NewStub(channel_)),
          cluster_stub_(ClusterService::NewStub(channel_)),
          address_(address) {}

    // ---------- DataNodeService ----------
    // Devuelve true si subió OK
    bool UploadBlock(int32_t block_id, const std::string& filename, const std::string& data, std::string* err_msg = nullptr);

    // Devuelve datos del bloque. Lanza std::runtime_error si falla (o devuelve false via out param si prefieres).
    bool DownloadBlock(int32_t block_id, const std::string& filename, std::string& out_data, std::string* err_msg = nullptr);

    // ---------- ClusterService (opcional pero recomendado) ----------
    bool RegisterDataNode(const std::string& datanode_id, const std::string& ip, int port, std::string* err_msg = nullptr);
    bool Heartbeat(const std::string& datanode_id, std::string* err_msg = nullptr);
    bool ReportBlock(const std::string& datanode_id, int32_t block_id, const std::string& filename, std::string* err_msg = nullptr);

    const std::string& address() const { return address_; }

private:
    std::shared_ptr<grpc::Channel> channel_;
    std::unique_ptr<DataNodeService::Stub> dn_stub_;
    std::unique_ptr<ClusterService::Stub>  cluster_stub_;
    std::string address_;
};

} // namespace dfs
