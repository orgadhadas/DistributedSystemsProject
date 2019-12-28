package protos;

import DS.zookeeper.ZKClientImpl;
import io.grpc.stub.StreamObserver;

public class VoteServiceImpl extends VoteServiceGrpc.VoteServiceImplBase {

    private ZKClientImpl zkClient;

    public VoteServiceImpl(ZKClientImpl zkClient){
        this.zkClient = zkClient;
    }

    @Override
    public void startElections(StartRequest request, StreamObserver<StartResponse> responseObserver) {
        if (zkClient.getGlobalLeader() == zkClient.serverId) {
            // pass to all local leaders and wait for response
        }
        else{
            // pass to global leader and wait for response
        }
    }

    @Override
    public void startElectionsInternal(StartInternalRequest request, StreamObserver<StartInternalResponse> responseStreamObserver){
        if (zkClient.getLocalLeader() == zkClient.serverId){
            // pass to all servers in shard
        }
        else {
            // just update your var
        }
    }


}
