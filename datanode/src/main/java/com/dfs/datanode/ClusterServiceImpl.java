package com.dfs.datanode;

import io.grpc.stub.StreamObserver;
import dfs.ClusterServiceGrpc;
import dfs.Dfs;

public class ClusterServiceImpl extends ClusterServiceGrpc.ClusterServiceImplBase {

    @Override
    public void registerDataNode(Dfs.RegisterDataNodeRequest request,
                                 StreamObserver<Dfs.RegisterDataNodeResponse> responseObserver) {
        System.out.println("[DataNode] Registro recibido: " + request.getDatanodeId()
                           + " (" + request.getIpAddress() + ":" + request.getPort() + ")");
        Dfs.RegisterDataNodeResponse response = Dfs.RegisterDataNodeResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void heartbeat(Dfs.HeartbeatRequest request,
                          StreamObserver<Dfs.HeartbeatResponse> responseObserver) {
        System.out.println("[DataNode] Heartbeat recibido de: " + request.getDatanodeId());
        Dfs.HeartbeatResponse response = Dfs.HeartbeatResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // (Opcional) ReportBlock también lo puedes implementar aquí
}
