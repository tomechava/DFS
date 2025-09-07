#pragma once
#include <string>
#include <unordered_map>
#include <vector>

// Información de un bloque registrado en la tabla de metadatos
struct BlockLocation {
    std::string block_id;
    std::string datanode_address;
};

class MetadataManager {
public:
    // Registrar en la tabla que un bloque de un archivo está en cierto DataNode
    void registerBlockLocation(const std::string& filename, const BlockLocation& block);

    // Obtener la lista de bloques (con sus DataNodes) de un archivo
    std::vector<BlockLocation> lookupFileBlocks(const std::string& filename) const;

    // Listar los archivos de un usuario
    std::vector<std::string> listFilesForUser(const std::string& username) const;

    // Eliminar la referencia a un archivo (desregistrar de la tabla)
    void unregisterFile(const std::string& filename);

private:
    // filename -> lista de ubicaciones de bloques
    std::unordered_map<std::string, std::vector<BlockLocation>> fileTable;

    // username -> lista de archivos que le pertenecen
    std::unordered_map<std::string, std::vector<std::string>> userFiles;
};
