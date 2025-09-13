#include "HashUtils.h"
#include <functional>
#include <limits>

namespace dfs {

// =============================
// Hash de 64 bits (FNV-1a)
// =============================
uint64_t HashUtils::hash64(const std::string& input) {
    const uint64_t fnv_prime = 1099511628211ULL;
    const uint64_t offset_basis = 1469598103934665603ULL;

    uint64_t hash = offset_basis;
    for (char c : input) {
        hash ^= static_cast<uint64_t>(c);
        hash *= fnv_prime;
    }
    return hash;
}

// splitmix64: buen mezclador rápido de 64 bits
static uint64_t splitmix64(uint64_t x) {
    x += 0x9e3779b97f4a7c15ULL;
    x = (x ^ (x >> 30)) * 0xbf58476d1ce4e5b9ULL;
    x = (x ^ (x >> 27)) * 0x94d049bb133111ebULL;
    x = x ^ (x >> 31);
    return x;
}

// =============================
// Generador robusto de block id
// =============================
uint64_t HashUtils::blockIdFor(const std::string& filename, uint64_t index) {
    // Hash base del filename
    uint64_t h_name = hash64(filename);

    // Hash del filename + index (similar a lo que hacías antes)
    std::string key = filename + ":" + std::to_string(index);
    uint64_t h_key = hash64(key);

    // Mezclamos con splitmix64 para dispersar los bits y reducir colisiones
    uint64_t mixed = h_name;
    mixed ^= splitmix64(h_key + 0x9e3779b97f4a7c15ULL + (mixed << 6) + (mixed >> 2));
    mixed = splitmix64(mixed ^ (index + 0x9e3779b97f4a7c15ULL));

    // Como queremos un id con "significado" estable, devolvemos mixed
    // (si en tu sistema necesitas signed int64, castealo al usarlo)
    return mixed;
}

// =============================
// Rendezvous Hashing (HRW)
// =============================
std::string HashUtils::rendezvousSelect(
    const std::string& key,
    const std::vector<std::string>& nodes) 
{
    if (nodes.empty()) return "";

    uint64_t maxScore = std::numeric_limits<uint64_t>::min();
    std::string bestNode;

    for (const auto& node : nodes) {
        // Combinar key + node en un hash
        std::string combined = key + "_" + node;
        uint64_t score = hash64(combined);

        if (score > maxScore || bestNode.empty()) {
            maxScore = score;
            bestNode = node;
        }
    }

    return bestNode;
}

// =============================
// Particionamiento por hash
// =============================
int HashUtils::getPartition(const std::string& key, int numPartitions) {
    if (numPartitions <= 0) return 0;
    uint64_t h = hash64(key);
    return static_cast<int>(h % numPartitions);
}

} // namespace dfs
