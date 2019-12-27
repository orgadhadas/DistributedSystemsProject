package DS.zookeeper;
import org.apache.zookeeper.*;

import java.io.IOException;

public class ZKClientImpl implements Watcher {
    private ZooKeeper zk;
    private int shardId;
    private int serverId;
    private int localLeader;
    private int globalLeader;

    public String root = "/ELECTIONS";

    public ZKClientImpl(String zkHost, int shardId, int serverId){
        try {
            this.zk = new ZooKeeper(zkHost, 3000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.shardId = shardId;
        this.serverId = serverId;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    private void initZK(){

    }

    private void signToLeaders(){

    }
}
