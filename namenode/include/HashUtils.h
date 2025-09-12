#pragma once
#include <string>
#include <cstdint>
#include <vector>
#include <utility>

namespace dfs {

class HashUtils {
public:
    // Hash básico de 64 bits para strings
    static uint64_t hash64(const std::string& input);

    // Selecciona el DataNode más apto usando Rendezvous Hashing
    static std::string rendezvousSelect(
        const std::string& key,
        const std::vector<std::string>& nodes);

    // Particionamiento: asigna un bloque a un número de partición
    static int getPartition(
        const std::string& key,
        int numPartitions);
};

} // namespace dfs
