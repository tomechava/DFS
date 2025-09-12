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

// =============================
// Rendezvous Hashing (HRW)
// =============================
std::string HashUtils::rendezvousSelect(
    const std::string& key,
    const std::vector<std::string>& nodes) 
{
    if (nodes.empty()) return "";

    uint64_t maxScore = 0;
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
