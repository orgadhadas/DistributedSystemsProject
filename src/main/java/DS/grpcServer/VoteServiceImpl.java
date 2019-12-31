package DS.grpcServer;

import DS.elections.Elections;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import protos.*;
import DS.zookeeper.ZKClientImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import protos.Test;
import protos.TestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VoteServiceImpl extends VoteServiceGrpc.VoteServiceImplBase {

    private ZKClientImpl zkClient;
    private Elections elections;

    public VoteServiceImpl(ZKClientImpl zkClient, Elections elections) {
        this.zkClient = zkClient;
        this.elections = elections;
    }

    @Override
    public void startElections(StartRequest request, StreamObserver<StartResponse> responseObserver) {
        StartResponse response;
        if(!zkClient.getSystemUp()){
            zkClient.triggerSystemUpZnode();
            response = StartResponse.newBuilder().setMsg("Started successfully").build();
        }
        else{
            response = StartResponse.newBuilder().setMsg("Already started").build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void stopElections(StopRequest request, StreamObserver<StopResponse> responseObserver) {
        StopResponse.Builder builder = StopResponse.newBuilder();
        if (zkClient.getSystemUp()){
            zkClient.triggerSystemUpZnode();
            builder.setMsg("Stopped successfully");
        }
        else{
            builder.setMsg("Already stopped");
        }

        List<Integer> leaders = zkClient.getAllLocalLeaders();
        List<VoteStatusInternalResponse> responsesFromLeaders = new ArrayList<>(elections.NUMBER_OF_STATES);
        for (int i = 0; i < elections.NUMBER_OF_STATES; i++){
            responsesFromLeaders.add(null);
        }
        while (!getVoteStatusFromLeaders(leaders, responsesFromLeaders)) {}

        StopResponse response = builder.addAllVotes(responsesFromLeaders).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addVoteInternal(VoteRequest request, StreamObserver<VoteResponse> responseStreamObserver) {
        if (zkClient.getLocalLeader() == zkClient.serverId) {
            if (!zkClient.getSystemUp()) {
                VoteResponse response = VoteResponse.newBuilder().setStarted(false).build();
                responseStreamObserver.onNext(response);
                responseStreamObserver.onCompleted();
                return;
            }

            System.out.println("Start Add Vote Internal - as local leader");

            System.out.println("waiting for previous commit to finish");
            zkClient.waitForCommitBerrier();

            System.out.println("clearing previous commit");
            zkClient.clearPrevCommitAsLeader();

            List<Integer> liveServers = zkClient.getLiveLocalServers();
            for (int i : liveServers) {
                if (i == zkClient.serverId) continue;
                String IP = "172.18." + zkClient.shardId + "." + (i % 10);
                ManagedChannel channel = ManagedChannelBuilder.forAddress(IP, 8081)
                        .usePlaintext()
                        .build();

                VoteServiceGrpc.VoteServiceBlockingStub stub
                        = VoteServiceGrpc.newBlockingStub(channel);

                VoteResponse voteResponse = stub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS)
                        .addVoteInternal(request);
                System.out.println("done " + i);

                channel.shutdown();
            }


            System.out.println("updating doCommit");
            zkClient.prepareCommitAsLeader();

            System.out.println("commiting");
            elections.setVote(request.getPersonId(), request.getVote());
            zkClient.commit();

            System.out.println("FINISHED COMMIT - Add Vote");
            VoteResponse response = VoteResponse.newBuilder().setStarted(true).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        } else {
            // get addVote from leader and send ok
            System.out.println("Start Add Vote Internal - as NONLeader server");

            System.out.println("waiting for doCommit");
            zkClient.prepareCommitAsRegular(); // turn on watcher on doCommit
            VoteResponse response = VoteResponse.newBuilder().setStarted(true).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();

            while (!zkClient.getCommitFlag()) {
            }
            zkClient.setCommitFlag(false);

            // update vote in database

            System.out.println("commiting");
            elections.setVote(request.getPersonId(), request.getVote());
            zkClient.commit();
            System.out.println("FINISHED COMMIT - Add Vote");
        }
    }

    @Override
    public void test(Test request, StreamObserver<TestResponse> responseStreamObserver) {
        System.out.println("got test message");
        TestResponse response = TestResponse.newBuilder().build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getVoteStatus(VoteStatusRequest request, StreamObserver<VoteStatusResponse> responseStreamObserver) {
        System.out.println("Starting getVoteStatus");

        List<Integer> leaders = zkClient.getAllLocalLeaders();

        List<VoteStatusInternalResponse> responsesFromLeaders = new ArrayList<>(elections.NUMBER_OF_STATES);
        for (int i = 0; i < elections.NUMBER_OF_STATES; i++){
            responsesFromLeaders.add(null);
        }

        while (!getVoteStatusFromLeaders(leaders, responsesFromLeaders)) {}

        VoteStatusResponse.Builder responseBuilder = VoteStatusResponse.newBuilder();
        responseBuilder.addAllVotes(responsesFromLeaders);

        VoteStatusResponse response = responseBuilder.build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();

        System.out.println("Finished getVoteStatus");
    }

    private boolean getVoteStatusFromLeaders(List<Integer> leaders, List<VoteStatusInternalResponse> responsesFromLeaders) {
        try {
            for (int i : leaders) {
                String IP = "172.18." + (i / 10) + "." + (i % 10);
                System.out.println("ip " + IP);

                ManagedChannel channel = ManagedChannelBuilder.forAddress(IP, 8081)
                        .usePlaintext()
                        .build();

                VoteServiceGrpc.VoteServiceBlockingStub stub
                        = VoteServiceGrpc.newBlockingStub(channel);

                VoteStatusInternalResponse voteResponse = stub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS)
                        .getVoteStatusInternal(VoteStatusInternalRequest.newBuilder().build());
                responsesFromLeaders.set((i / 10)-1, voteResponse);
                System.out.println("done " + i);

                channel.shutdown();
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                return false;
            } else {
                throw e;
            }
        }

        return true;
    }

    @Override
    public void getVoteStatusInternal(VoteStatusInternalRequest request, StreamObserver<VoteStatusInternalResponse> responseStreamObserver) {
        int electorals = elections.getElectorals();
        List<Integer> votes = elections.getResults();

        System.out.println("electorals: " + electorals);
        System.out.println("votes: " + votes);
        VoteStatusInternalResponse response = VoteStatusInternalResponse.newBuilder().setElectorals(electorals)
                .addAllVotes(votes).build();
        System.out.println("voteResponse " + response);

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

}
