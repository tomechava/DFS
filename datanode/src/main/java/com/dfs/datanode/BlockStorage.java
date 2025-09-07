package com.dfs.datanode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class BlockStorage {
    private final String storageDir;

    public BlockStorage(String storageDir) {
        this.storageDir = storageDir;
        new File(storageDir).mkdirs(); // crear dir si no existe
    }

    public void saveBlock(int blockId, String filename, byte[] data) throws IOException {
        File blockFile = new File(storageDir, filename + "_" + blockId + ".blk");
        try (FileOutputStream fos = new FileOutputStream(blockFile)) {
            fos.write(data);
        }
    }

    public byte[] readBlock(int blockId, String filename) throws IOException {
        File blockFile = new File(storageDir, filename + "_" + blockId + ".blk");
        return Files.readAllBytes(blockFile.toPath());
    }
}
