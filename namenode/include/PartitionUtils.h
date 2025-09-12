#pragma once
#include <string>
#include <vector>
#include "HashUtils.h"
#include "MetadataManager.h"

namespace dfs {

class PartitionUtils {
public:
    // Selecciona DataNodes para un bloque dado
    static std::pair<std::string, std::vector<std::string>>
    assignBlockNodes(
        const std::string& filename,
        int64_t blockId,
        const std::vector<std::string>& allNodes,
        int replicationFactor = 3);

    // Determina a qué partición lógica pertenece un bloque
    static int getBlockPartition(
        const std::string& filename,
        int64_t blockId,
        int numPartitions);
};

} // namespace dfs
