package com.dfs.datanode;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import dfs.DataNodeServiceGrpc;
import dfs.Dfs;
import dfs.NameNodeServiceGrpc;
import dfs.ClusterServiceGrpc; //Para llamar RegisterDataNode y Heartbeat

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DataNodeServer {

    private final int port;
    private Server server;

    private static final String NAMENODE_ADDRESS = "localhost:50052"; // NameNode gRPC
    private final String dataNodeId;
    private final String ip;
    private final int nnPort = 50052;
    private final String storageDir;

    private ManagedChannel nnChannel;
    private ClusterServiceGrpc.ClusterServiceBlockingStub clusterStub;

    public DataNodeServer(int port, String storageDir, String dataNodeId, String ip) {
        this.port = port;
        this.dataNodeId = dataNodeId;
        this.ip = ip;
        this.storageDir = storageDir;
    }
    

    public void start() throws IOException {
        // Canal hacia el NameNode
        nnChannel = ManagedChannelBuilder.forTarget(NAMENODE_ADDRESS)
                                         .usePlaintext()
                                         .build();
        clusterStub = ClusterServiceGrpc.newBlockingStub(nnChannel);
        NameNodeServiceGrpc.NameNodeServiceBlockingStub nameNodeStub = NameNodeServiceGrpc.newBlockingStub(nnChannel);
    
        // Iniciar gRPC server del DataNode
        this.server = ServerBuilder.forPort(port)
                .maxInboundMessageSize(100 * 1024 * 1024)
                .addService(new DataNodeServiceImpl(storageDir, clusterStub, dataNodeId, nameNodeStub))
                .build()
                .start();
    
        System.out.println("DataNode gRPC server started on port " + port);
    
        // 1. Registrarse en el NameNode
        register();
    
        // 2. Heartbeats
        startHeartbeats();
    
        // Hook para apagado limpio
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down DataNode server...");
            DataNodeServer.this.stop();
        }));
    }
    

    private void register() {
        System.out.println("Registrando DataNode en NameNode...");

        Dfs.RegisterDataNodeRequest req = Dfs.RegisterDataNodeRequest.newBuilder()
                .setDatanodeId(dataNodeId)
                .setIpAddress(ip)
                .setPort(port)
                .build();

        Dfs.RegisterDataNodeResponse resp = clusterStub.registerDataNode(req);
        if (resp.getSuccess()) {
            System.out.println("Registro exitoso en el NameNode");
        } else {
            System.err.println("Fallo al registrar DataNode en el NameNode");
        }
    }

    private void startHeartbeats() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Dfs.HeartbeatRequest req = Dfs.HeartbeatRequest.newBuilder()
                            .setDatanodeId(dataNodeId)
                            .build();

                    Dfs.HeartbeatResponse resp = clusterStub.heartbeat(req);
                    if (resp.getSuccess()) {
                        System.out.println("Heartbeat enviado OK");
                    } else {
                        System.err.println("Heartbeat rechazado");
                    }
                } catch (Exception e) {
                    System.err.println("Error enviando heartbeat: " + e.getMessage());
                }
            }
        }, 0, 30000); // cada 30 segundos
    }

    public void stop() {
        if (server != null) server.shutdown();
        if (nnChannel != null) nnChannel.shutdown();
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) server.awaitTermination();
    }

    // ------------------- Servicio del DataNode -------------------
    static class DataNodeServiceImpl extends DataNodeServiceGrpc.DataNodeServiceImplBase {
        private final BlockStorage storage;
        private final ClusterServiceGrpc.ClusterServiceBlockingStub clusterStub;
        private final String dataNodeId;
        private final NameNodeServiceGrpc.NameNodeServiceBlockingStub nameNodeStub;

    
        public DataNodeServiceImpl(String storageDir, 
                           ClusterServiceGrpc.ClusterServiceBlockingStub clusterStub,
                           String dataNodeId, 
                           NameNodeServiceGrpc.NameNodeServiceBlockingStub nameNodeStub) {
            this.storage = new BlockStorage(storageDir);
            this.clusterStub = clusterStub;
            this.dataNodeId = dataNodeId;
            this.nameNodeStub = nameNodeStub;
        }

    
        @Override
        public void uploadBlock(Dfs.BlockUploadRequest request, StreamObserver<Dfs.BlockUploadResponse> responseObserver) {
            System.out.println("Recibiendo bloque (primario): " + request.getFilename() + " (ID: " + request.getBlockId() + ")");
            try {
                // 1️Guardar en disco
                storage.saveBlock(request.getBlockId(), request.getFilename(), request.getData().toByteArray());

                // 2️Reportar al NameNode
                reportBlockToNameNode(request.getBlockId(), request.getFilename());

                // 3️Consultar réplicas en el NameNode
                Dfs.ReplicaRequest replicaReq = Dfs.ReplicaRequest.newBuilder()
                        .setBlockId((int) request.getBlockId()) // OJO: aquí depende de tu proto (int32/int64)
                        .build();

                Dfs.ReplicaResponse replicaResp = nameNodeStub.getReplicas(replicaReq);

                for (String replicaAddress : replicaResp.getReplicaDatanodesList()) {
                    try {
                        System.out.println("Enviando réplica a " + replicaAddress);

                        // Abrir canal al DataNode réplica
                        ManagedChannel channel = ManagedChannelBuilder.forTarget(replicaAddress)
                                .usePlaintext()
                                .build();
                        DataNodeServiceGrpc.DataNodeServiceBlockingStub dnStub = DataNodeServiceGrpc.newBlockingStub(channel);

                        // Enviar réplica
                        Dfs.BlockReplicationRequest replicationRequest = Dfs.BlockReplicationRequest.newBuilder()
                                .setBlockId((int) request.getBlockId()) // igual, depende de proto
                                .setFilename(request.getFilename())
                                .setData(request.getData())
                                .build();

                        Dfs.BlockReplicationResponse replicationResponse = dnStub.replicateBlock(replicationRequest);

                        if (replicationResponse.getSuccess()) {
                            System.out.println("Réplica enviada correctamente a " + replicaAddress);
                        } else {
                            System.err.println("Fallo al replicar en " + replicaAddress);
                        }

                        channel.shutdown();

                    } catch (Exception e) {
                        System.err.println("Error replicando bloque a " + replicaAddress + ": " + e.getMessage());
                    }
                }

                // Responder al cliente
                Dfs.BlockUploadResponse response = Dfs.BlockUploadResponse.newBuilder()
                        .setSuccess(true)
                        .build();
                responseObserver.onNext(response);

            } catch (Exception e) {
                System.err.println("Error guardando bloque primario: " + e.getMessage());
                Dfs.BlockUploadResponse response = Dfs.BlockUploadResponse.newBuilder()
                        .setSuccess(false)
                        .build();
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();
        }

    
        @Override
        public void replicateBlock(Dfs.BlockReplicationRequest request, StreamObserver<Dfs.BlockReplicationResponse> responseObserver) {
            System.out.println("Recibiendo bloque de REPLICACIÓN: " +
                    request.getFilename() + " (ID: " + request.getBlockId() + ")");
            try {
                storage.saveBlock(request.getBlockId(), request.getFilename(), request.getData().toByteArray());

                // Reportar al NameNode que tengo este bloque
                reportBlockToNameNode(request.getBlockId(), request.getFilename());

                Dfs.BlockReplicationResponse response = Dfs.BlockReplicationResponse.newBuilder()
                        .setSuccess(true)
                        .build();
                responseObserver.onNext(response);

            } catch (Exception e) {
                System.err.println("Error guardando réplica: " + e.getMessage());
                Dfs.BlockReplicationResponse response = Dfs.BlockReplicationResponse.newBuilder()
                        .setSuccess(false)
                        .build();
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();
        }

    
        // Método para reportar bloques al NameNode
        private void reportBlockToNameNode(long blockId, String filename) {
            try {
                Dfs.BlockReport blockReport = Dfs.BlockReport.newBuilder()
                        .setBlockId(blockId)
                        .setFilename(filename)
                        .build();
    
                Dfs.ReportBlockRequest request = Dfs.ReportBlockRequest.newBuilder()
                        .setDatanodeId(this.dataNodeId)
                        .addBlocks(blockReport)
                        .build();
    
                Dfs.ReportBlockResponse response = clusterStub.reportBlock(request);
    
                if (response.getSuccess()) {
                    System.out.println("Bloque " + blockId + " reportado al NameNode con éxito.");
                } else {
                    System.err.println("Fallo al reportar bloque " + blockId + " al NameNode.");
                }
            } catch (Exception e) {
                System.err.println("Error al reportar bloque " + blockId + ": " + e.getMessage());
            }
        }
    
        @Override
        public void downloadBlock(Dfs.BlockDownloadRequest request, StreamObserver<Dfs.BlockDownloadResponse> responseObserver) {
            System.out.println("Enviando bloque: " + request.getFilename() + " (ID: " + request.getBlockId() + ")");
            try {
                byte[] data = storage.readBlock(request.getBlockId(), request.getFilename());
    
                Dfs.BlockDownloadResponse response = Dfs.BlockDownloadResponse.newBuilder()
                        .setData(ByteString.copyFrom(data))
                        .build();
    
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (Exception e) {
                System.err.println("Error leyendo bloque: " + e.getMessage());
                responseObserver.onError(e);
            }
        }
    }
    

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 4) {
            System.out.println("Uso: DataNodeServer <port> <storageDir> <dataNodeId> <ip>");
            return;
        }
    
        int port = Integer.parseInt(args[0]);
        String storageDir = args[1];
        String dataNodeId = args[2];
        String ip = args[3];
    
        DataNodeServer server = new DataNodeServer(port, storageDir, dataNodeId, ip);
        server.start();
        server.blockUntilShutdown();
    }
    
}
