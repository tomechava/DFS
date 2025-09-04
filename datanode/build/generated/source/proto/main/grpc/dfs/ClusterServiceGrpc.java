package dfs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ===========================================================
 *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
 * ===========================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: dfs.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ClusterServiceGrpc {

  private ClusterServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "dfs.ClusterService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.RegisterDataNodeRequest,
      dfs.Dfs.RegisterDataNodeResponse> getRegisterDataNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterDataNode",
      requestType = dfs.Dfs.RegisterDataNodeRequest.class,
      responseType = dfs.Dfs.RegisterDataNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.RegisterDataNodeRequest,
      dfs.Dfs.RegisterDataNodeResponse> getRegisterDataNodeMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.RegisterDataNodeRequest, dfs.Dfs.RegisterDataNodeResponse> getRegisterDataNodeMethod;
    if ((getRegisterDataNodeMethod = ClusterServiceGrpc.getRegisterDataNodeMethod) == null) {
      synchronized (ClusterServiceGrpc.class) {
        if ((getRegisterDataNodeMethod = ClusterServiceGrpc.getRegisterDataNodeMethod) == null) {
          ClusterServiceGrpc.getRegisterDataNodeMethod = getRegisterDataNodeMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.RegisterDataNodeRequest, dfs.Dfs.RegisterDataNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterDataNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.RegisterDataNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.RegisterDataNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClusterServiceMethodDescriptorSupplier("RegisterDataNode"))
              .build();
        }
      }
    }
    return getRegisterDataNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.HeartbeatRequest,
      dfs.Dfs.HeartbeatResponse> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Heartbeat",
      requestType = dfs.Dfs.HeartbeatRequest.class,
      responseType = dfs.Dfs.HeartbeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.HeartbeatRequest,
      dfs.Dfs.HeartbeatResponse> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.HeartbeatRequest, dfs.Dfs.HeartbeatResponse> getHeartbeatMethod;
    if ((getHeartbeatMethod = ClusterServiceGrpc.getHeartbeatMethod) == null) {
      synchronized (ClusterServiceGrpc.class) {
        if ((getHeartbeatMethod = ClusterServiceGrpc.getHeartbeatMethod) == null) {
          ClusterServiceGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.HeartbeatRequest, dfs.Dfs.HeartbeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.HeartbeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.HeartbeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClusterServiceMethodDescriptorSupplier("Heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.ReportBlockRequest,
      dfs.Dfs.ReportBlockResponse> getReportBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportBlock",
      requestType = dfs.Dfs.ReportBlockRequest.class,
      responseType = dfs.Dfs.ReportBlockResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.ReportBlockRequest,
      dfs.Dfs.ReportBlockResponse> getReportBlockMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.ReportBlockRequest, dfs.Dfs.ReportBlockResponse> getReportBlockMethod;
    if ((getReportBlockMethod = ClusterServiceGrpc.getReportBlockMethod) == null) {
      synchronized (ClusterServiceGrpc.class) {
        if ((getReportBlockMethod = ClusterServiceGrpc.getReportBlockMethod) == null) {
          ClusterServiceGrpc.getReportBlockMethod = getReportBlockMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.ReportBlockRequest, dfs.Dfs.ReportBlockResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ReportBlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ReportBlockResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClusterServiceMethodDescriptorSupplier("ReportBlock"))
              .build();
        }
      }
    }
    return getReportBlockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClusterServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterServiceStub>() {
        @java.lang.Override
        public ClusterServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterServiceStub(channel, callOptions);
        }
      };
    return ClusterServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClusterServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterServiceBlockingStub>() {
        @java.lang.Override
        public ClusterServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterServiceBlockingStub(channel, callOptions);
        }
      };
    return ClusterServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClusterServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterServiceFutureStub>() {
        @java.lang.Override
        public ClusterServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterServiceFutureStub(channel, callOptions);
        }
      };
    return ClusterServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ===========================================================
   *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
   * ===========================================================
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Registro inicial de un DataNode
     * </pre>
     */
    default void registerDataNode(dfs.Dfs.RegisterDataNodeRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.RegisterDataNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterDataNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Heartbeat: DataNode avisa que sigue activo
     * </pre>
     */
    default void heartbeat(dfs.Dfs.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     * <pre>
     * Reporte de bloques almacenados
     * </pre>
     */
    default void reportBlock(dfs.Dfs.ReportBlockRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ReportBlockResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportBlockMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ClusterService.
   * <pre>
   * ===========================================================
   *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
   * ===========================================================
   * </pre>
   */
  public static abstract class ClusterServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ClusterServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ClusterService.
   * <pre>
   * ===========================================================
   *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
   * ===========================================================
   * </pre>
   */
  public static final class ClusterServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ClusterServiceStub> {
    private ClusterServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registro inicial de un DataNode
     * </pre>
     */
    public void registerDataNode(dfs.Dfs.RegisterDataNodeRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.RegisterDataNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterDataNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Heartbeat: DataNode avisa que sigue activo
     * </pre>
     */
    public void heartbeat(dfs.Dfs.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Reporte de bloques almacenados
     * </pre>
     */
    public void reportBlock(dfs.Dfs.ReportBlockRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ReportBlockResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportBlockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ClusterService.
   * <pre>
   * ===========================================================
   *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
   * ===========================================================
   * </pre>
   */
  public static final class ClusterServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ClusterServiceBlockingStub> {
    private ClusterServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registro inicial de un DataNode
     * </pre>
     */
    public dfs.Dfs.RegisterDataNodeResponse registerDataNode(dfs.Dfs.RegisterDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterDataNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Heartbeat: DataNode avisa que sigue activo
     * </pre>
     */
    public dfs.Dfs.HeartbeatResponse heartbeat(dfs.Dfs.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Reporte de bloques almacenados
     * </pre>
     */
    public dfs.Dfs.ReportBlockResponse reportBlock(dfs.Dfs.ReportBlockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportBlockMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ClusterService.
   * <pre>
   * ===========================================================
   *  Servicio interno NameNode &lt;-&gt; DataNodes (Cluster Management)
   * ===========================================================
   * </pre>
   */
  public static final class ClusterServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ClusterServiceFutureStub> {
    private ClusterServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registro inicial de un DataNode
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.RegisterDataNodeResponse> registerDataNode(
        dfs.Dfs.RegisterDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterDataNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Heartbeat: DataNode avisa que sigue activo
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.HeartbeatResponse> heartbeat(
        dfs.Dfs.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Reporte de bloques almacenados
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.ReportBlockResponse> reportBlock(
        dfs.Dfs.ReportBlockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportBlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_DATA_NODE = 0;
  private static final int METHODID_HEARTBEAT = 1;
  private static final int METHODID_REPORT_BLOCK = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_DATA_NODE:
          serviceImpl.registerDataNode((dfs.Dfs.RegisterDataNodeRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.RegisterDataNodeResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((dfs.Dfs.HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.HeartbeatResponse>) responseObserver);
          break;
        case METHODID_REPORT_BLOCK:
          serviceImpl.reportBlock((dfs.Dfs.ReportBlockRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.ReportBlockResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterDataNodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.RegisterDataNodeRequest,
              dfs.Dfs.RegisterDataNodeResponse>(
                service, METHODID_REGISTER_DATA_NODE)))
        .addMethod(
          getHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.HeartbeatRequest,
              dfs.Dfs.HeartbeatResponse>(
                service, METHODID_HEARTBEAT)))
        .addMethod(
          getReportBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.ReportBlockRequest,
              dfs.Dfs.ReportBlockResponse>(
                service, METHODID_REPORT_BLOCK)))
        .build();
  }

  private static abstract class ClusterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClusterServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dfs.Dfs.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClusterService");
    }
  }

  private static final class ClusterServiceFileDescriptorSupplier
      extends ClusterServiceBaseDescriptorSupplier {
    ClusterServiceFileDescriptorSupplier() {}
  }

  private static final class ClusterServiceMethodDescriptorSupplier
      extends ClusterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ClusterServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ClusterServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClusterServiceFileDescriptorSupplier())
              .addMethod(getRegisterDataNodeMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getReportBlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
