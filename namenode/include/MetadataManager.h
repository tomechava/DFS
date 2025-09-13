#pragma once
#include <string>
#include <unordered_map>
#include <vector>
#include <algorithm>

struct BlockLocation {
    std::string block_id;
    std::string datanode_address;
};

struct NodeInfo {
    std::string address; // ip:port
    uint64_t capacity;
    uint64_t used;
    bool alive;
};

class MetadataManager {
public:
    // ==== DataNodes ====
    void registerDataNode(const std::string& datanode_id,
                          const std::string& ip,
                          int port);
    std::vector<std::string> getAllDataNodes() const;
    std::vector<std::string> getAliveDataNodes() const;

    // ==== Archivos y bloques ====
    void registerBlockLocation(const std::string& filename,
                               const BlockLocation& block);
    std::vector<BlockLocation> lookupFileBlocks(const std::string& filename) const;

    void unregisterFile(const std::string& filename);
    std::vector<std::string> listFilesForUser(const std::string& username) const;

    // ==== Réplicas ====
    void registerBlockReplicas(int64_t block_id,
                               const std::vector<std::string>& nodes);
    std::vector<std::string> getReplicasForBlock(int64_t block_id) const;

private:
    // id → dirección (ip:port)
    std::unordered_map<std::string, std::string> dataNodes;
    // id → info detallada
    std::unordered_map<std::string, NodeInfo> nodeInfo;
    // archivo → lista de bloques
    std::unordered_map<std::string, std::vector<BlockLocation>> fileTable;
    // usuario → lista de archivos
    std::unordered_map<std::string, std::vector<std::string>> userFiles;
    // blockId → réplicas
    std::unordered_map<int64_t, std::vector<std::string>> blockReplicas;
};
