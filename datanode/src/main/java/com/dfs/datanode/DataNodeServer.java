// Servidor gRPC que recibe bloques

package com.dfs.datanode;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import dfs.DataNodeServiceGrpc;
import dfs.Dfs;
import com.google.protobuf.ByteString;

import java.io.IOException;

public class DataNodeServer {

    private final int port;
    private final Server server;

    public DataNodeServer(int port, String storageDir) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .maxInboundMessageSize(100 * 1024 * 1024) // 100 MB
                .addService(new DataNodeServiceImpl(storageDir))
                .addService(new ClusterServiceImpl()) //Servicio de registro / heartbeat
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("✅ DataNode gRPC server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down DataNode server...");
            DataNodeServer.this.stop();
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    // Implementación del servicio definido en dfs.proto
    static class DataNodeServiceImpl extends DataNodeServiceGrpc.DataNodeServiceImplBase {
        private final BlockStorage storage;

        public DataNodeServiceImpl(String storageDir) {
            this.storage = new BlockStorage(storageDir);
        }

        @Override
        public void uploadBlock(Dfs.BlockUploadRequest request, StreamObserver<Dfs.BlockUploadResponse> responseObserver) {
            System.out.println("📥 Recibiendo bloque: " + request.getFilename() + " (ID: " + request.getBlockId() + ")");

            try {
                storage.saveBlock(request.getBlockId(),
                        request.getFilename(),
                        request.getData().toByteArray());

                Dfs.BlockUploadResponse response = Dfs.BlockUploadResponse.newBuilder()
                        .setSuccess(true)
                        .build();
                responseObserver.onNext(response);
            } catch (Exception e) {
                System.err.println("❌ Error guardando bloque: " + e.getMessage());
                Dfs.BlockUploadResponse response = Dfs.BlockUploadResponse.newBuilder()
                        .setSuccess(false)
                        .build();
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();
        }

        @Override
        public void downloadBlock(Dfs.BlockDownloadRequest request, StreamObserver<Dfs.BlockDownloadResponse> responseObserver) {
            System.out.println("Enviando bloque: " + request.getFilename() + " (ID: " + request.getBlockId() + ")");

            try{
                byte[] data = storage.readBlock(request.getBlockId(), request.getFilename());

                Dfs.BlockDownloadResponse response = Dfs.BlockDownloadResponse.newBuilder()
                        .setData(ByteString.copyFrom(data))
                        .build();
                
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
            } catch (Exception e) {
                System.err.println("❌ Error leyendo bloque: " + e.getMessage());
                responseObserver.onError(e);
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        String storageDir = "data/blocks"; // Carpeta donde se guardan los bloques

        DataNodeServer server = new DataNodeServer(port, storageDir);
        server.start();
        server.blockUntilShutdown();
    }
}

