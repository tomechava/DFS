#include "PartitionUtils.h"
#include <unordered_set>
#include <iostream>

namespace dfs {

// ==========================================
// Selecciona primario y réplicas para un bloque
// ==========================================
std::pair<std::string, std::vector<std::string>>

PartitionUtils::assignBlockNodes(
    const std::string& filename,
    int64_t blockId,
    const std::vector<std::string>& allNodes,
    int replicationFactor)
{
    std::pair<std::string, std::vector<std::string>> result;

    if (allNodes.empty()) {
        std::cerr << "No hay DataNodes disponibles para asignar bloque "
                  << blockId << std::endl;
        return result;
    }

    // Clave única por bloque
    std::string blockKey = filename + "_" + std::to_string(blockId);

    // 1. Seleccionamos primario usando HRW (Rendezvous)
    std::string primary = HashUtils::rendezvousSelect(blockKey, allNodes);
    result.first = primary;

    // 2. Seleccionamos réplicas adicionales
    std::unordered_set<std::string> chosen;
    chosen.insert(primary);

    for (int r = 1; r < replicationFactor && chosen.size() < allNodes.size(); r++) {
        std::string seed = blockKey + "_replica_" + std::to_string(r);
        std::string candidate = HashUtils::rendezvousSelect(seed, allNodes);

        // Evitamos duplicados
        while (chosen.count(candidate)) {
            seed += "_x";
            candidate = HashUtils::rendezvousSelect(seed, allNodes);
        }

        chosen.insert(candidate);
        result.second.push_back(candidate);
    }

    return result;
}

// ==========================================
// Particionamiento por hash
// ==========================================
int PartitionUtils::getBlockPartition(
    const std::string& filename,
    int64_t blockId,
    int numPartitions)
{
    if (numPartitions <= 0) return 0;
    std::string key = filename + "_" + std::to_string(blockId);
    return HashUtils::getPartition(key, numPartitions);
}

} // namespace dfs
