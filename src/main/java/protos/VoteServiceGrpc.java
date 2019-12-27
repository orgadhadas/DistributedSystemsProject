package protos;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: vote.proto")
public final class VoteServiceGrpc {

  private VoteServiceGrpc() {}

  public static final String SERVICE_NAME = "protos.VoteService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<protos.VoteRequest,
      protos.VoteResponse> getAddVoteInternalMethod;

  public static io.grpc.MethodDescriptor<protos.VoteRequest,
      protos.VoteResponse> getAddVoteInternalMethod() {
    io.grpc.MethodDescriptor<protos.VoteRequest, protos.VoteResponse> getAddVoteInternalMethod;
    if ((getAddVoteInternalMethod = VoteServiceGrpc.getAddVoteInternalMethod) == null) {
      synchronized (VoteServiceGrpc.class) {
        if ((getAddVoteInternalMethod = VoteServiceGrpc.getAddVoteInternalMethod) == null) {
          VoteServiceGrpc.getAddVoteInternalMethod = getAddVoteInternalMethod = 
              io.grpc.MethodDescriptor.<protos.VoteRequest, protos.VoteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protos.VoteService", "addVoteInternal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.VoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.VoteResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new VoteServiceMethodDescriptorSupplier("addVoteInternal"))
                  .build();
          }
        }
     }
     return getAddVoteInternalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<protos.StartRequest,
      protos.StartResponse> getStartVoteInternalMethod;

  public static io.grpc.MethodDescriptor<protos.StartRequest,
      protos.StartResponse> getStartVoteInternalMethod() {
    io.grpc.MethodDescriptor<protos.StartRequest, protos.StartResponse> getStartVoteInternalMethod;
    if ((getStartVoteInternalMethod = VoteServiceGrpc.getStartVoteInternalMethod) == null) {
      synchronized (VoteServiceGrpc.class) {
        if ((getStartVoteInternalMethod = VoteServiceGrpc.getStartVoteInternalMethod) == null) {
          VoteServiceGrpc.getStartVoteInternalMethod = getStartVoteInternalMethod = 
              io.grpc.MethodDescriptor.<protos.StartRequest, protos.StartResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protos.VoteService", "startVoteInternal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.StartRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.StartResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new VoteServiceMethodDescriptorSupplier("startVoteInternal"))
                  .build();
          }
        }
     }
     return getStartVoteInternalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<protos.StopRequest,
      protos.StopResponse> getStopVoteInternalMethod;

  public static io.grpc.MethodDescriptor<protos.StopRequest,
      protos.StopResponse> getStopVoteInternalMethod() {
    io.grpc.MethodDescriptor<protos.StopRequest, protos.StopResponse> getStopVoteInternalMethod;
    if ((getStopVoteInternalMethod = VoteServiceGrpc.getStopVoteInternalMethod) == null) {
      synchronized (VoteServiceGrpc.class) {
        if ((getStopVoteInternalMethod = VoteServiceGrpc.getStopVoteInternalMethod) == null) {
          VoteServiceGrpc.getStopVoteInternalMethod = getStopVoteInternalMethod = 
              io.grpc.MethodDescriptor.<protos.StopRequest, protos.StopResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protos.VoteService", "stopVoteInternal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.StopRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.StopResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new VoteServiceMethodDescriptorSupplier("stopVoteInternal"))
                  .build();
          }
        }
     }
     return getStopVoteInternalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<protos.VoteStatusRequest,
      protos.VoteStatusResponse> getGetVoteStatusInternalMethod;

  public static io.grpc.MethodDescriptor<protos.VoteStatusRequest,
      protos.VoteStatusResponse> getGetVoteStatusInternalMethod() {
    io.grpc.MethodDescriptor<protos.VoteStatusRequest, protos.VoteStatusResponse> getGetVoteStatusInternalMethod;
    if ((getGetVoteStatusInternalMethod = VoteServiceGrpc.getGetVoteStatusInternalMethod) == null) {
      synchronized (VoteServiceGrpc.class) {
        if ((getGetVoteStatusInternalMethod = VoteServiceGrpc.getGetVoteStatusInternalMethod) == null) {
          VoteServiceGrpc.getGetVoteStatusInternalMethod = getGetVoteStatusInternalMethod = 
              io.grpc.MethodDescriptor.<protos.VoteStatusRequest, protos.VoteStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protos.VoteService", "getVoteStatusInternal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.VoteStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.VoteStatusResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new VoteServiceMethodDescriptorSupplier("getVoteStatusInternal"))
                  .build();
          }
        }
     }
     return getGetVoteStatusInternalMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VoteServiceStub newStub(io.grpc.Channel channel) {
    return new VoteServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VoteServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new VoteServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VoteServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new VoteServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class VoteServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addVoteInternal(protos.VoteRequest request,
        io.grpc.stub.StreamObserver<protos.VoteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAddVoteInternalMethod(), responseObserver);
    }

    /**
     */
    public void startVoteInternal(protos.StartRequest request,
        io.grpc.stub.StreamObserver<protos.StartResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartVoteInternalMethod(), responseObserver);
    }

    /**
     */
    public void stopVoteInternal(protos.StopRequest request,
        io.grpc.stub.StreamObserver<protos.StopResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStopVoteInternalMethod(), responseObserver);
    }

    /**
     */
    public void getVoteStatusInternal(protos.VoteStatusRequest request,
        io.grpc.stub.StreamObserver<protos.VoteStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVoteStatusInternalMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddVoteInternalMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                protos.VoteRequest,
                protos.VoteResponse>(
                  this, METHODID_ADD_VOTE_INTERNAL)))
          .addMethod(
            getStartVoteInternalMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                protos.StartRequest,
                protos.StartResponse>(
                  this, METHODID_START_VOTE_INTERNAL)))
          .addMethod(
            getStopVoteInternalMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                protos.StopRequest,
                protos.StopResponse>(
                  this, METHODID_STOP_VOTE_INTERNAL)))
          .addMethod(
            getGetVoteStatusInternalMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                protos.VoteStatusRequest,
                protos.VoteStatusResponse>(
                  this, METHODID_GET_VOTE_STATUS_INTERNAL)))
          .build();
    }
  }

  /**
   */
  public static final class VoteServiceStub extends io.grpc.stub.AbstractStub<VoteServiceStub> {
    private VoteServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VoteServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VoteServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VoteServiceStub(channel, callOptions);
    }

    /**
     */
    public void addVoteInternal(protos.VoteRequest request,
        io.grpc.stub.StreamObserver<protos.VoteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddVoteInternalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void startVoteInternal(protos.StartRequest request,
        io.grpc.stub.StreamObserver<protos.StartResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartVoteInternalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void stopVoteInternal(protos.StopRequest request,
        io.grpc.stub.StreamObserver<protos.StopResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStopVoteInternalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVoteStatusInternal(protos.VoteStatusRequest request,
        io.grpc.stub.StreamObserver<protos.VoteStatusResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVoteStatusInternalMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class VoteServiceBlockingStub extends io.grpc.stub.AbstractStub<VoteServiceBlockingStub> {
    private VoteServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VoteServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VoteServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VoteServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public protos.VoteResponse addVoteInternal(protos.VoteRequest request) {
      return blockingUnaryCall(
          getChannel(), getAddVoteInternalMethod(), getCallOptions(), request);
    }

    /**
     */
    public protos.StartResponse startVoteInternal(protos.StartRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartVoteInternalMethod(), getCallOptions(), request);
    }

    /**
     */
    public protos.StopResponse stopVoteInternal(protos.StopRequest request) {
      return blockingUnaryCall(
          getChannel(), getStopVoteInternalMethod(), getCallOptions(), request);
    }

    /**
     */
    public protos.VoteStatusResponse getVoteStatusInternal(protos.VoteStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVoteStatusInternalMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class VoteServiceFutureStub extends io.grpc.stub.AbstractStub<VoteServiceFutureStub> {
    private VoteServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VoteServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VoteServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VoteServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protos.VoteResponse> addVoteInternal(
        protos.VoteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAddVoteInternalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protos.StartResponse> startVoteInternal(
        protos.StartRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartVoteInternalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protos.StopResponse> stopVoteInternal(
        protos.StopRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStopVoteInternalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protos.VoteStatusResponse> getVoteStatusInternal(
        protos.VoteStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVoteStatusInternalMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_VOTE_INTERNAL = 0;
  private static final int METHODID_START_VOTE_INTERNAL = 1;
  private static final int METHODID_STOP_VOTE_INTERNAL = 2;
  private static final int METHODID_GET_VOTE_STATUS_INTERNAL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VoteServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VoteServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_VOTE_INTERNAL:
          serviceImpl.addVoteInternal((protos.VoteRequest) request,
              (io.grpc.stub.StreamObserver<protos.VoteResponse>) responseObserver);
          break;
        case METHODID_START_VOTE_INTERNAL:
          serviceImpl.startVoteInternal((protos.StartRequest) request,
              (io.grpc.stub.StreamObserver<protos.StartResponse>) responseObserver);
          break;
        case METHODID_STOP_VOTE_INTERNAL:
          serviceImpl.stopVoteInternal((protos.StopRequest) request,
              (io.grpc.stub.StreamObserver<protos.StopResponse>) responseObserver);
          break;
        case METHODID_GET_VOTE_STATUS_INTERNAL:
          serviceImpl.getVoteStatusInternal((protos.VoteStatusRequest) request,
              (io.grpc.stub.StreamObserver<protos.VoteStatusResponse>) responseObserver);
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

  private static abstract class VoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VoteServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return protos.Vote.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VoteService");
    }
  }

  private static final class VoteServiceFileDescriptorSupplier
      extends VoteServiceBaseDescriptorSupplier {
    VoteServiceFileDescriptorSupplier() {}
  }

  private static final class VoteServiceMethodDescriptorSupplier
      extends VoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VoteServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (VoteServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VoteServiceFileDescriptorSupplier())
              .addMethod(getAddVoteInternalMethod())
              .addMethod(getStartVoteInternalMethod())
              .addMethod(getStopVoteInternalMethod())
              .addMethod(getGetVoteStatusInternalMethod())
              .build();
        }
      }
    }
    return result;
  }
}
