package dfs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ===========================================================
 *  Servicio NameNode &lt;-&gt; Cliente
 * ===========================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: dfs.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NameNodeServiceGrpc {

  private NameNodeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "dfs.NameNodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.PutFileRequest,
      dfs.Dfs.PutFileResponse> getPutFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutFile",
      requestType = dfs.Dfs.PutFileRequest.class,
      responseType = dfs.Dfs.PutFileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.PutFileRequest,
      dfs.Dfs.PutFileResponse> getPutFileMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.PutFileRequest, dfs.Dfs.PutFileResponse> getPutFileMethod;
    if ((getPutFileMethod = NameNodeServiceGrpc.getPutFileMethod) == null) {
      synchronized (NameNodeServiceGrpc.class) {
        if ((getPutFileMethod = NameNodeServiceGrpc.getPutFileMethod) == null) {
          NameNodeServiceGrpc.getPutFileMethod = getPutFileMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.PutFileRequest, dfs.Dfs.PutFileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.PutFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.PutFileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeServiceMethodDescriptorSupplier("PutFile"))
              .build();
        }
      }
    }
    return getPutFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.GetFileRequest,
      dfs.Dfs.GetFileResponse> getGetFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetFile",
      requestType = dfs.Dfs.GetFileRequest.class,
      responseType = dfs.Dfs.GetFileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.GetFileRequest,
      dfs.Dfs.GetFileResponse> getGetFileMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.GetFileRequest, dfs.Dfs.GetFileResponse> getGetFileMethod;
    if ((getGetFileMethod = NameNodeServiceGrpc.getGetFileMethod) == null) {
      synchronized (NameNodeServiceGrpc.class) {
        if ((getGetFileMethod = NameNodeServiceGrpc.getGetFileMethod) == null) {
          NameNodeServiceGrpc.getGetFileMethod = getGetFileMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.GetFileRequest, dfs.Dfs.GetFileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.GetFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.GetFileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeServiceMethodDescriptorSupplier("GetFile"))
              .build();
        }
      }
    }
    return getGetFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.ListFilesRequest,
      dfs.Dfs.ListFilesResponse> getListFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListFiles",
      requestType = dfs.Dfs.ListFilesRequest.class,
      responseType = dfs.Dfs.ListFilesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.ListFilesRequest,
      dfs.Dfs.ListFilesResponse> getListFilesMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.ListFilesRequest, dfs.Dfs.ListFilesResponse> getListFilesMethod;
    if ((getListFilesMethod = NameNodeServiceGrpc.getListFilesMethod) == null) {
      synchronized (NameNodeServiceGrpc.class) {
        if ((getListFilesMethod = NameNodeServiceGrpc.getListFilesMethod) == null) {
          NameNodeServiceGrpc.getListFilesMethod = getListFilesMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.ListFilesRequest, dfs.Dfs.ListFilesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ListFilesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ListFilesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeServiceMethodDescriptorSupplier("ListFiles"))
              .build();
        }
      }
    }
    return getListFilesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.RemoveFileRequest,
      dfs.Dfs.RemoveFileResponse> getRemoveFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveFile",
      requestType = dfs.Dfs.RemoveFileRequest.class,
      responseType = dfs.Dfs.RemoveFileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.RemoveFileRequest,
      dfs.Dfs.RemoveFileResponse> getRemoveFileMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.RemoveFileRequest, dfs.Dfs.RemoveFileResponse> getRemoveFileMethod;
    if ((getRemoveFileMethod = NameNodeServiceGrpc.getRemoveFileMethod) == null) {
      synchronized (NameNodeServiceGrpc.class) {
        if ((getRemoveFileMethod = NameNodeServiceGrpc.getRemoveFileMethod) == null) {
          NameNodeServiceGrpc.getRemoveFileMethod = getRemoveFileMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.RemoveFileRequest, dfs.Dfs.RemoveFileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.RemoveFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.RemoveFileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeServiceMethodDescriptorSupplier("RemoveFile"))
              .build();
        }
      }
    }
    return getRemoveFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<dfs.Dfs.ReplicaRequest,
      dfs.Dfs.ReplicaResponse> getGetReplicasMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetReplicas",
      requestType = dfs.Dfs.ReplicaRequest.class,
      responseType = dfs.Dfs.ReplicaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dfs.Dfs.ReplicaRequest,
      dfs.Dfs.ReplicaResponse> getGetReplicasMethod() {
    io.grpc.MethodDescriptor<dfs.Dfs.ReplicaRequest, dfs.Dfs.ReplicaResponse> getGetReplicasMethod;
    if ((getGetReplicasMethod = NameNodeServiceGrpc.getGetReplicasMethod) == null) {
      synchronized (NameNodeServiceGrpc.class) {
        if ((getGetReplicasMethod = NameNodeServiceGrpc.getGetReplicasMethod) == null) {
          NameNodeServiceGrpc.getGetReplicasMethod = getGetReplicasMethod =
              io.grpc.MethodDescriptor.<dfs.Dfs.ReplicaRequest, dfs.Dfs.ReplicaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetReplicas"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ReplicaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dfs.Dfs.ReplicaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeServiceMethodDescriptorSupplier("GetReplicas"))
              .build();
        }
      }
    }
    return getGetReplicasMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NameNodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceStub>() {
        @java.lang.Override
        public NameNodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeServiceStub(channel, callOptions);
        }
      };
    return NameNodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NameNodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceBlockingStub>() {
        @java.lang.Override
        public NameNodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeServiceBlockingStub(channel, callOptions);
        }
      };
    return NameNodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NameNodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeServiceFutureStub>() {
        @java.lang.Override
        public NameNodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeServiceFutureStub(channel, callOptions);
        }
      };
    return NameNodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ===========================================================
   *  Servicio NameNode &lt;-&gt; Cliente
   * ===========================================================
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Subir un archivo: cliente indica nombre y tamaño, NN responde con asignación de bloques
     * </pre>
     */
    default void putFile(dfs.Dfs.PutFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.PutFileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * Descargar un archivo: cliente pide info de bloques
     * </pre>
     */
    default void getFile(dfs.Dfs.GetFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.GetFileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * Listar archivos del usuario
     * </pre>
     */
    default void listFiles(dfs.Dfs.ListFilesRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ListFilesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListFilesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Eliminar un archivo
     * </pre>
     */
    default void removeFile(dfs.Dfs.RemoveFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.RemoveFileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * Consulta de réplicas para un bloque
     * </pre>
     */
    default void getReplicas(dfs.Dfs.ReplicaRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ReplicaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetReplicasMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NameNodeService.
   * <pre>
   * ===========================================================
   *  Servicio NameNode &lt;-&gt; Cliente
   * ===========================================================
   * </pre>
   */
  public static abstract class NameNodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NameNodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NameNodeService.
   * <pre>
   * ===========================================================
   *  Servicio NameNode &lt;-&gt; Cliente
   * ===========================================================
   * </pre>
   */
  public static final class NameNodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NameNodeServiceStub> {
    private NameNodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un archivo: cliente indica nombre y tamaño, NN responde con asignación de bloques
     * </pre>
     */
    public void putFile(dfs.Dfs.PutFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.PutFileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Descargar un archivo: cliente pide info de bloques
     * </pre>
     */
    public void getFile(dfs.Dfs.GetFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.GetFileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Listar archivos del usuario
     * </pre>
     */
    public void listFiles(dfs.Dfs.ListFilesRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ListFilesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Eliminar un archivo
     * </pre>
     */
    public void removeFile(dfs.Dfs.RemoveFileRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.RemoveFileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Consulta de réplicas para un bloque
     * </pre>
     */
    public void getReplicas(dfs.Dfs.ReplicaRequest request,
        io.grpc.stub.StreamObserver<dfs.Dfs.ReplicaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetReplicasMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NameNodeService.
   * <pre>
   * ===========================================================
   *  Servicio NameNode &lt;-&gt; Cliente
   * ===========================================================
   * </pre>
   */
  public static final class NameNodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NameNodeServiceBlockingStub> {
    private NameNodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un archivo: cliente indica nombre y tamaño, NN responde con asignación de bloques
     * </pre>
     */
    public dfs.Dfs.PutFileResponse putFile(dfs.Dfs.PutFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Descargar un archivo: cliente pide info de bloques
     * </pre>
     */
    public dfs.Dfs.GetFileResponse getFile(dfs.Dfs.GetFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Listar archivos del usuario
     * </pre>
     */
    public dfs.Dfs.ListFilesResponse listFiles(dfs.Dfs.ListFilesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListFilesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Eliminar un archivo
     * </pre>
     */
    public dfs.Dfs.RemoveFileResponse removeFile(dfs.Dfs.RemoveFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Consulta de réplicas para un bloque
     * </pre>
     */
    public dfs.Dfs.ReplicaResponse getReplicas(dfs.Dfs.ReplicaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetReplicasMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NameNodeService.
   * <pre>
   * ===========================================================
   *  Servicio NameNode &lt;-&gt; Cliente
   * ===========================================================
   * </pre>
   */
  public static final class NameNodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NameNodeServiceFutureStub> {
    private NameNodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subir un archivo: cliente indica nombre y tamaño, NN responde con asignación de bloques
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.PutFileResponse> putFile(
        dfs.Dfs.PutFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutFileMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Descargar un archivo: cliente pide info de bloques
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.GetFileResponse> getFile(
        dfs.Dfs.GetFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetFileMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Listar archivos del usuario
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.ListFilesResponse> listFiles(
        dfs.Dfs.ListFilesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Eliminar un archivo
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.RemoveFileResponse> removeFile(
        dfs.Dfs.RemoveFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveFileMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Consulta de réplicas para un bloque
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<dfs.Dfs.ReplicaResponse> getReplicas(
        dfs.Dfs.ReplicaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetReplicasMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT_FILE = 0;
  private static final int METHODID_GET_FILE = 1;
  private static final int METHODID_LIST_FILES = 2;
  private static final int METHODID_REMOVE_FILE = 3;
  private static final int METHODID_GET_REPLICAS = 4;

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
        case METHODID_PUT_FILE:
          serviceImpl.putFile((dfs.Dfs.PutFileRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.PutFileResponse>) responseObserver);
          break;
        case METHODID_GET_FILE:
          serviceImpl.getFile((dfs.Dfs.GetFileRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.GetFileResponse>) responseObserver);
          break;
        case METHODID_LIST_FILES:
          serviceImpl.listFiles((dfs.Dfs.ListFilesRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.ListFilesResponse>) responseObserver);
          break;
        case METHODID_REMOVE_FILE:
          serviceImpl.removeFile((dfs.Dfs.RemoveFileRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.RemoveFileResponse>) responseObserver);
          break;
        case METHODID_GET_REPLICAS:
          serviceImpl.getReplicas((dfs.Dfs.ReplicaRequest) request,
              (io.grpc.stub.StreamObserver<dfs.Dfs.ReplicaResponse>) responseObserver);
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
          getPutFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.PutFileRequest,
              dfs.Dfs.PutFileResponse>(
                service, METHODID_PUT_FILE)))
        .addMethod(
          getGetFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.GetFileRequest,
              dfs.Dfs.GetFileResponse>(
                service, METHODID_GET_FILE)))
        .addMethod(
          getListFilesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.ListFilesRequest,
              dfs.Dfs.ListFilesResponse>(
                service, METHODID_LIST_FILES)))
        .addMethod(
          getRemoveFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.RemoveFileRequest,
              dfs.Dfs.RemoveFileResponse>(
                service, METHODID_REMOVE_FILE)))
        .addMethod(
          getGetReplicasMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dfs.Dfs.ReplicaRequest,
              dfs.Dfs.ReplicaResponse>(
                service, METHODID_GET_REPLICAS)))
        .build();
  }

  private static abstract class NameNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NameNodeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dfs.Dfs.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NameNodeService");
    }
  }

  private static final class NameNodeServiceFileDescriptorSupplier
      extends NameNodeServiceBaseDescriptorSupplier {
    NameNodeServiceFileDescriptorSupplier() {}
  }

  private static final class NameNodeServiceMethodDescriptorSupplier
      extends NameNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NameNodeServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (NameNodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NameNodeServiceFileDescriptorSupplier())
              .addMethod(getPutFileMethod())
              .addMethod(getGetFileMethod())
              .addMethod(getListFilesMethod())
              .addMethod(getRemoveFileMethod())
              .addMethod(getGetReplicasMethod())
              .build();
        }
      }
    }
    return result;
  }
}
