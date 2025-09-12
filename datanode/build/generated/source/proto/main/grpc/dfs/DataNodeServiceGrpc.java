package dfs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ===========================================================
 *  Servicio Cliente &lt;-&gt; DataNode
 * ===========================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: dfs.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DataNodeServiceGrpc {

  private DataNodeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "dfs.DataNodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.BlockUploadRequest,
      dfs.Dfs.BlockUploadResponse> getUploadBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadBlock",
      requestType = dfs.Dfs.BlockUploadRequest.class,
      responseType = dfs.Dfs.BlockUploadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.BlockUploadRequest,
      dfs.Dfs.BlockUploadResponse> getUploadBlockMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.BlockUploadRequest, dfs.Dfs.BlockUploadResponse> getUploadBlockMethod;
    if ((getUploadBlockMethod = DataNodeServiceGrpc.getUploadBlockMethod) == null) {
      synchronized (DataNodeServiceGrpc.class) {
        if ((getUploadBlockMethod = DataNodeServiceGrpc.getUploadBlockMethod) == null) {
          DataNodeServiceGrpc.getUploadBlockMethod = getUploadBlockMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.BlockUploadRequest, dfs.Dfs.BlockUploadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockUploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockUploadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataNodeServiceMethodDescriptorSupplier("UploadBlock"))
              .build();
        }
      }
    }
    return getUploadBlockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.BlockDownloadRequest,
      dfs.Dfs.BlockDownloadResponse> getDownloadBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadBlock",
      requestType = dfs.Dfs.BlockDownloadRequest.class,
      responseType = dfs.Dfs.BlockDownloadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.BlockDownloadRequest,
      dfs.Dfs.BlockDownloadResponse> getDownloadBlockMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.BlockDownloadRequest, dfs.Dfs.BlockDownloadResponse> getDownloadBlockMethod;
    if ((getDownloadBlockMethod = DataNodeServiceGrpc.getDownloadBlockMethod) == null) {
      synchronized (DataNodeServiceGrpc.class) {
        if ((getDownloadBlockMethod = DataNodeServiceGrpc.getDownloadBlockMethod) == null) {
          DataNodeServiceGrpc.getDownloadBlockMethod = getDownloadBlockMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.BlockDownloadRequest, dfs.Dfs.BlockDownloadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockDownloadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockDownloadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataNodeServiceMethodDescriptorSupplier("DownloadBlock"))
              .build();
        }
      }
    }
    return getDownloadBlockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.BlockReplicationRequest,
      dfs.Dfs.BlockReplicationResponse> getReplicateBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReplicateBlock",
      requestType = dfs.Dfs.BlockReplicationRequest.class,
      responseType = dfs.Dfs.BlockReplicationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.BlockReplicationRequest,
      dfs.Dfs.BlockReplicationResponse> getReplicateBlockMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.BlockReplicationRequest, dfs.Dfs.BlockReplicationResponse> getReplicateBlockMethod;
    if ((getReplicateBlockMethod = DataNodeServiceGrpc.getReplicateBlockMethod) == null) {
      synchronized (DataNodeServiceGrpc.class) {
        if ((getReplicateBlockMethod = DataNodeServiceGrpc.getReplicateBlockMethod) == null) {
          DataNodeServiceGrpc.getReplicateBlockMethod = getReplicateBlockMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.BlockReplicationRequest, dfs.Dfs.BlockReplicationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReplicateBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockReplicationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.BlockReplicationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataNodeServiceMethodDescriptorSupplier("ReplicateBlock"))
              .build();
        }
      }
    }
    return getReplicateBlockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataNodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceStub>() {
        @java.lang.Override
        public DataNodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeServiceStub(channel, callOptions);
        }
      };
    return DataNodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataNodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceBlockingStub>() {
        @java.lang.Override
        public DataNodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeServiceBlockingStub(channel, callOptions);
        }
      };
    return DataNodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataNodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeServiceFutureStub>() {
        @java.lang.Override
        public DataNodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeServiceFutureStub(channel, callOptions);
        }
      };
    return DataNodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ===========================================================
   *  Servicio Cliente &lt;-&gt; DataNode
   * ===========================================================
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Subir un bloque a un DataNode
     * </pre>
     */
    default void uploadBlock(dfs.Dfs.BlockUploadRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockUploadResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUploadBlockMethod(), responseObserver);
    }

    /**
     * <pre>
     * Descargar un bloque desde un DataNode
     * </pre>
     */
    default void downloadBlock(dfs.Dfs.BlockDownloadRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockDownloadResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadBlockMethod(), responseObserver);
    }

    /**
     * <pre>
     * Replicación de bloques entre DataNodes
     * </pre>
     */
    default void replicateBlock(dfs.Dfs.BlockReplicationRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockReplicationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReplicateBlockMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DataNodeService.
   * <pre>
   * ===========================================================
   *  Servicio Cliente &lt;-&gt; DataNode
   * ===========================================================
   * </pre>
   */
  public static abstract class DataNodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return DataNodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DataNodeService.
   * <pre>
   * ===========================================================
   *  Servicio Cliente &lt;-&gt; DataNode
   * ===========================================================
   * </pre>
   */
  public static final class DataNodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DataNodeServiceStub> {
    private DataNodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un bloque a un DataNode
     * </pre>
     */
    public void uploadBlock(dfs.Dfs.BlockUploadRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockUploadResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUploadBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Descargar un bloque desde un DataNode
     * </pre>
     */
    public void downloadBlock(dfs.Dfs.BlockDownloadRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockDownloadResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDownloadBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Replicación de bloques entre DataNodes
     * </pre>
     */
    public void replicateBlock(dfs.Dfs.BlockReplicationRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.BlockReplicationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReplicateBlockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DataNodeService.
   * <pre>
   * ===========================================================
   *  Servicio Cliente &lt;-&gt; DataNode
   * ===========================================================
   * </pre>
   */
  public static final class DataNodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DataNodeServiceBlockingStub> {
    private DataNodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un bloque a un DataNode
     * </pre>
     */
    public dfs.Dfs.BlockUploadResponse uploadBlock(dfs.Dfs.BlockUploadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUploadBlockMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Descargar un bloque desde un DataNode
     * </pre>
     */
    public dfs.Dfs.BlockDownloadResponse downloadBlock(dfs.Dfs.BlockDownloadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDownloadBlockMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Replicación de bloques entre DataNodes
     * </pre>
     */
    public dfs.Dfs.BlockReplicationResponse replicateBlock(dfs.Dfs.BlockReplicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReplicateBlockMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DataNodeService.
   * <pre>
   * ===========================================================
   *  Servicio Cliente &lt;-&gt; DataNode
   * ===========================================================
   * </pre>
   */
  public static final class DataNodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DataNodeServiceFutureStub> {
    private DataNodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un bloque a un DataNode
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.BlockUploadResponse> uploadBlock(
        dfs.Dfs.BlockUploadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUploadBlockMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Descargar un bloque desde un DataNode
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.BlockDownloadResponse> downloadBlock(
        dfs.Dfs.BlockDownloadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDownloadBlockMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Replicación de bloques entre DataNodes
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.BlockReplicationResponse> replicateBlock(
        dfs.Dfs.BlockReplicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReplicateBlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPLOAD_BLOCK = 0;
  private static final int METHODID_DOWNLOAD_BLOCK = 1;
  private static final int METHODID_REPLICATE_BLOCK = 2;

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
        case METHODID_UPLOAD_BLOCK:
          serviceImpl.uploadBlock((dfs.Dfs.BlockUploadRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.BlockUploadResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_BLOCK:
          serviceImpl.downloadBlock((dfs.Dfs.BlockDownloadRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.BlockDownloadResponse>) responseObserver);
          break;
        case METHODID_REPLICATE_BLOCK:
          serviceImpl.replicateBlock((dfs.Dfs.BlockReplicationRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.BlockReplicationResponse>) responseObserver);
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
          getUploadBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.BlockUploadRequest,
              dfs.Dfs.BlockUploadResponse>(
                service, METHODID_UPLOAD_BLOCK)))
        .addMethod(
          getDownloadBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.BlockDownloadRequest,
              dfs.Dfs.BlockDownloadResponse>(
                service, METHODID_DOWNLOAD_BLOCK)))
        .addMethod(
          getReplicateBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.BlockReplicationRequest,
              dfs.Dfs.BlockReplicationResponse>(
                service, METHODID_REPLICATE_BLOCK)))
        .build();
  }

  private static abstract class DataNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DataNodeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dfs.Dfs.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DataNodeService");
    }
  }

  private static final class DataNodeServiceFileDescriptorSupplier
      extends DataNodeServiceBaseDescriptorSupplier {
    DataNodeServiceFileDescriptorSupplier() {}
  }

  private static final class DataNodeServiceMethodDescriptorSupplier
      extends DataNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    DataNodeServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (DataNodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DataNodeServiceFileDescriptorSupplier())
              .addMethod(getUploadBlockMethod())
              .addMethod(getDownloadBlockMethod())
              .addMethod(getReplicateBlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
