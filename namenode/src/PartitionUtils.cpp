#include "PartitionUtils.h"
#include <unordered_set>
#include <iostream>
#include <algorithm> // std::min

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

    // Ajustar replicationFactor al máximo posible
    int effectiveReplication = std::min(replicationFactor, (int)allNodes.size());

    // Clave única por bloque
    std::string blockKey = filename + "_" + std::to_string(blockId);

    // 1. Seleccionamos primario usando HRW (Rendezvous)
    std::string primary = HashUtils::rendezvousSelect(blockKey, allNodes);
    result.first = primary;

    // 2. Seleccionamos réplicas adicionales
    std::unordered_set<std::string> chosen;
    chosen.insert(primary); // aseguramos que el primario no entre en réplicas

    for (int r = 1; (int)result.second.size() < effectiveReplication - 1; r++) {
        std::string seed = blockKey + "_replica_" + std::to_string(r);
        std::string candidate = HashUtils::rendezvousSelect(seed, allNodes);

        // Evitamos duplicados y el mismo primario
        int attempts = 0;
        while (chosen.count(candidate) && attempts < 10) {
            seed += "_x";
            candidate = HashUtils::rendezvousSelect(seed, allNodes);
            attempts++;
        }
        if (chosen.count(candidate)) {
            break; // no se encontró nodo distinto, salimos
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
