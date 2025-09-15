#include "MetadataManager.h"
#include <iostream>
#include <sstream>
#include <algorithm> // std::remove
#include <random>
#include <chrono>
#include <thread>

MetadataManager::MetadataManager() {
    //precargar usuarios
    users["testuser"] = "1234";
}

void MetadataManager::registerDataNode(const std::string& datanode_id,
                                       const std::string& ip,
                                       int port) {
    std::string address = ip + ":" + std::to_string(port);

    NodeInfo info;
    info.address = address;
    info.capacity = 0;
    info.used = 0;
    info.alive = true;
    info.lastHeartbeat = std::chrono::steady_clock::now();

    dataNodes[datanode_id] = address;
    nodeInfo[datanode_id] = info;

    std::cout << "DataNode registrado: " << datanode_id
              << " @ " << address << std::endl;
}

void MetadataManager::updateHeartbeat(const std::string& datanode_id,
                                      const std::string& ip,
                                      int port) {
    std::lock_guard<std::mutex> lock(mtx);
    auto it = nodeInfo.find(datanode_id);
    if (it != nodeInfo.end()) {
        it->second.lastHeartbeat = std::chrono::steady_clock::now();
        it->second.alive = true;
    } else {
        // Nodo no estaba registrado, lo registramos aquí también
        std::string address = ip + ":" + std::to_string(port);

        NodeInfo info;
        info.address = address;
        info.capacity = 0;
        info.used = 0;
        info.alive = true;
        info.lastHeartbeat = std::chrono::steady_clock::now();

        nodeInfo[datanode_id] = info;
        dataNodes[datanode_id] = address;

        std::cout << "Heartbeat recibido de nodo no registrado, auto-registrado: "
                  << datanode_id << " @ " << address << std::endl;
    }
}

std::vector<std::string> MetadataManager::getAllDataNodes() const {
    std::vector<std::string> result;
    for (const auto& entry : dataNodes) {
        result.push_back(entry.second); // siempre ip:port
    }
    return result;
}

std::vector<std::string> MetadataManager::getAliveDataNodes() const {
    std::vector<std::string> alive;
    for (const auto& [id, info] : nodeInfo) {
        if (info.alive) {
            alive.push_back(info.address); // ip:port
        }
    }
    return alive;
}

// ----------------------
// Monitor en background
// ----------------------
void MetadataManager::monitorHeartbeats(int timeoutSeconds) {
    std::thread([this, timeoutSeconds]() {
        while (true) {
            {
                std::lock_guard<std::mutex> lock(mtx);
                auto now = std::chrono::steady_clock::now();
                for (auto& [id, info] : nodeInfo) {
                    auto diff = std::chrono::duration_cast<std::chrono::seconds>(now - info.lastHeartbeat).count();
                    if (diff > timeoutSeconds && info.alive) {
                        info.alive = false;
                        std::cerr << "DataNode marcado como caído: " << id
                                  << " (" << info.address << ")" << std::endl;
                    }
                }
            }
            std::this_thread::sleep_for(std::chrono::seconds(5));
        }
    }).detach();
}

void MetadataManager::registerBlockLocation(const std::string& filename,
                                            const BlockLocation& block) {
    fileTable[filename].push_back(block);
}

std::vector<BlockLocation> MetadataManager::lookupFileBlocks(const std::string& filename) const {
    auto it = fileTable.find(filename);
    if (it != fileTable.end()) {
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

std::vector<std::string> MetadataManager::listFilesForUser(const std::string& username) const {
    auto it = userFiles.find(username);
    if (it != userFiles.end()) {
        return it->second;
    }
    return {};
}

void MetadataManager::registerBlockReplicas(int64_t block_id,
                                            const std::vector<std::string>& nodes) {
    blockReplicas[block_id] = nodes; // ip:port de cada réplica
}

std::vector<std::string> MetadataManager::getReplicasForBlock(int64_t block_id) const {
    auto it = blockReplicas.find(block_id);
    if (it != blockReplicas.end()) {
        return it->second;
    }
    return {};
}

// ==== Usuarios ====
bool MetadataManager::validateUser(const std::string& username, const std::string& password) {
    auto it = users.find(username);
    return it != users.end() && it->second == password;
}

std::string MetadataManager::generateToken() {
    static std::mt19937_64 rng(std::chrono::high_resolution_clock::now().time_since_epoch().count());
    static std::uniform_int_distribution<uint64_t> dist;

    std::stringstream ss;
    ss << std::hex << dist(rng) << dist(rng);
    return ss.str();
}

std::string MetadataManager::createSession(const std::string& username) {
    std::string token = generateToken();
    sessions[token] = username;
    return token;
}

bool MetadataManager::validateToken(const std::string& username, const std::string& token) {
    auto it = sessions.find(token);
    return it != sessions.end() && it->second == username;
}