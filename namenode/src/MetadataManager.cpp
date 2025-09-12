#include "MetadataManager.h"

void MetadataManager::registerDataNode(const std::string& datanode_id, const std::string& address) {
    dataNodes[datanode_id] = address;
    nodeInfo[datanode_id] = {address, 0, 0, true}; // básico
}

std::vector<std::string> MetadataManager::getAllDataNodes() const {
    std::vector<std::string> result;
    for (const auto& entry : dataNodes) {
        result.push_back(entry.second);
    }
    return result;
}

void MetadataManager::registerBlockLocation(const std::string& filename, const BlockLocation& block) {
    fileTable[filename].push_back(block);
}

std::vector<BlockLocation> MetadataManager::lookupFileBlocks(const std::string& filename) const {
    auto it = fileTable.find(filename);
    if (it != fileTable.end()) {
        return it->second;
    }
    return {};
}

std::vector<std::string> MetadataManager::listFilesForUser(const std::string& username) const {
    auto it = userFiles.find(username);
    if (it != userFiles.end()) {
        return it->second;
    }
    return {};
}

void MetadataManager::unregisterFile(const std::string& filename) {
    fileTable.erase(filename);

    for (auto& entry : userFiles) {
        auto& files = entry.second;
        files.erase(std::remove(files.begin(), files.end(), filename), files.end());
    }
}

// ===== Nuevos métodos =====
void MetadataManager::registerBlockReplicas(int64_t block_id, const std::vector<std::string>& nodes) {
    blockReplicas[block_id] = nodes;
}

std::vector<std::string> MetadataManager::getReplicasForBlock(int64_t block_id) const {
    auto it = blockReplicas.find(block_id);
    if (it != blockReplicas.end()) {
        return it->second;
    }
    return {};
}

std::vector<std::string> MetadataManager::getAliveDataNodes() const {
    std::vector<std::string> alive;
    for (const auto& [id, info] : nodeInfo) {
        if (info.alive) {
            alive.push_back(info.addr);
        }
    }
    return alive;
}
