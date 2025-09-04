// Servidor gRPC que recibe bloques

package com.dfs.datanode;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import dfs.DataNodeServiceGrpc;
import dfs.Dfs;

import java.io.IOException;

public class DataNodeServer {

    private final int port;
    private final Server server;

    public DataNodeServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new DataNodeServiceImpl())
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
        @Override
        public void uploadBlock(Dfs.BlockUploadRequest request, StreamObserver<Dfs.BlockUploadResponse> responseObserver) {
            System.out.println("📥 Recibiendo bloque: " + request.getFilename() + " (ID: " + request.getBlockId() + ")");
            Dfs.BlockUploadResponse response = Dfs.BlockUploadResponse.newBuilder()
                    .setSuccess(true)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void downloadBlock(Dfs.BlockDownloadRequest request, StreamObserver<Dfs.BlockDownloadResponse> responseObserver) {
            System.out.println("📤 Enviando bloque: " + request.getFilename() + " (ID: " + request.getBlockId() + ")");
            Dfs.BlockDownloadResponse response = Dfs.BlockDownloadResponse.newBuilder()
                    .setData(com.google.protobuf.ByteString.copyFromUtf8("FakeBlockData"))
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DataNodeServer server = new DataNodeServer(50051);
        server.start();
        server.blockUntilShutdown();
    }
}

