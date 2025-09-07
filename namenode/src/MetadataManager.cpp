#include "MetadataManager.h"

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

    // Borrar de userFiles también
    for (auto& entry : userFiles) {
        auto& files = entry.second;
        files.erase(std::remove(files.begin(), files.end(), filename), files.end());
    }
}
