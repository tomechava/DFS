#pragma once
#include <string>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <cstdint>

// Información de un bloque registrado en la tabla de metadatos
struct BlockLocation {
    std::string block_id;
    std::string datanode_address;
};

struct NodeInfo {
    std::string addr;
    uint64_t free_bytes;
    int64_t last_heartbeat;
    bool alive;
};

class MetadataManager {
public:
    

    void registerDataNode(const std::string& datanode_id, const std::string& address);
    std::vector<std::string> getAllDataNodes() const;

    void registerBlockLocation(const std::string& filename, const BlockLocation& block);
    std::vector<BlockLocation> lookupFileBlocks(const std::string& filename) const;

    std::vector<std::string> listFilesForUser(const std::string& username) const;
    void unregisterFile(const std::string& filename);

    // NUEVO: manejo de réplicas
    void registerBlockReplicas(int64_t block_id, const std::vector<std::string>& nodes);
    std::vector<std::string> getReplicasForBlock(int64_t block_id) const;

    // NUEVO: filtrar sólo nodos vivos
    std::vector<std::string> getAliveDataNodes() const;

private:
    std::unordered_map<std::string, std::vector<BlockLocation>> fileTable;
    std::unordered_map<std::string, std::vector<std::string>> userFiles;
    std::unordered_map<std::string, std::string> dataNodes;
    std::unordered_map<int64_t, std::vector<std::string>> blockReplicas;
    std::unordered_map<std::string, NodeInfo> nodeInfo;
};
