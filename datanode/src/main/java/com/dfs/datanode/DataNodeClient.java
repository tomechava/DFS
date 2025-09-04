package com.dfs.datanode;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import dfs.DataNodeServiceGrpc;
import dfs.Dfs; // este es el paquete donde quedó Dfs.java generado del proto

public class DataNodeClient {

    public static void main(String[] args) {
        // 1. Crear canal hacia el DataNodeServer
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // sin TLS
                .build();

        // 2. Crear stub síncrono para DataNodeService
        DataNodeServiceGrpc.DataNodeServiceBlockingStub stub =
                DataNodeServiceGrpc.newBlockingStub(channel);

        // 3. Construir request de prueba para subir un bloque
        Dfs.BlockUploadRequest request = Dfs.BlockUploadRequest.newBuilder()
                .setBlockId(1)
                .setFilename("test.txt")
                .setData(com.google.protobuf.ByteString.copyFromUtf8("Hola mundo desde cliente Java!"))
                .build();

        // 4. Llamar al método remoto
        Dfs.BlockUploadResponse response = stub.uploadBlock(request);

        // 5. Imprimir resultado
        System.out.println("¿Subida exitosa? " + response.getSuccess());

        // 6. Cerrar canal
        channel.shutdown();
    }
}