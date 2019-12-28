package DS.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ZKClientImpl implements Watcher {
    private ZooKeeper zk;
    public int shardId;
    public int serverId;
    private int localLeader;
    private int globalLeader;
    private String localLeaderZnodePath;
    private String globalLeaderZnodePath;

    private String root = "/ELECTIONS";
    private String globalLeaderPath = root + "/leaderGLOBAL";
    private String localLeaderPath = root + "/leaderLOCAL";
    public String localCommitPath = root + "/COMMIT";
    private String localLeaderShardPath;
    private String localCommitShardPath;

    private class LeaderData {
        String path;
        byte[] id;

        LeaderData(String path, byte[] id) {
            this.path = path;
            this.id = id;
        }
    }

    public ZKClientImpl(String zkHost, int shardId, int serverId) {
        try {
            this.zk = new ZooKeeper(zkHost, 10000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.shardId = shardId;
        this.serverId = serverId;
        localLeaderShardPath = root + "/leaderLOCAL/" + shardId;
        localCommitShardPath = root + "/COMMIT/" + shardId;

        initZK();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case NodeDeleted:
                if (localLeaderZnodePath.equals(watchedEvent.getPath())) {
                    electLocalLeader();
                    if (localLeader == serverId){
                        signToGlobalLeaders();
                    }
                } else if (globalLeaderZnodePath.equals(watchedEvent.getPath())) {
                    electLocalLeader(); // in case the global leader that fell was my local leader
                    electGlobalLeader();
                }
        }
    }

    public int getLocalLeader() {
        return localLeader;
    }

    public int getGlobalLeader() {
        return globalLeader;
    }

    private void initZK() {
        createIfDoesntExist(root);
        createIfDoesntExist(globalLeaderPath);
        createIfDoesntExist(localLeaderPath);
        createIfDoesntExist(localLeaderShardPath);
        createIfDoesntExist(localCommitPath);
        createIfDoesntExist(localCommitShardPath);

        signToLocalLeaders();
        electLocalLeader();
        if (localLeader == serverId) {
            signToGlobalLeaders();
        }

        electGlobalLeader();

    }

    private void createIfDoesntExist(String path) {
        try {
            Stat bla = zk.exists(path, false);
            if (bla == null) {
                System.out.println(bla);
                zk.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
        catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void signToLocalLeaders() {
        try {
            zk.create(localLeaderShardPath + "/e", String.valueOf(serverId).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void signToGlobalLeaders() {
        try {
            zk.create(globalLeaderPath + "/e", String.valueOf(serverId).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void electLocalLeader() {
        synchronized (this) {
            LeaderData res = electLeader(localLeaderShardPath);
            byte[] data = res.id;
            if (data != null) {
                localLeader = Integer.parseInt(new String(data));
                localLeaderZnodePath = res.path;
            }
        }
        System.out.println("Elected local leader: " + localLeader);
    }

    private void electGlobalLeader() {
        synchronized (this) {
            LeaderData res = electLeader(globalLeaderPath);
            byte[] data = res.id;
            if (data != null) {
                globalLeader = Integer.parseInt(new String(data));
                globalLeaderZnodePath = res.path;
            }
        }
        System.out.println("Elected global leader: " + globalLeader);
    }

    private LeaderData electLeader(String path) {
        List<String> children = null;
        try {
            children = zk.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(children);
        byte[] data = null;
        String leaderPath = null;
        for (String leader : children) {
            try {
                zk.exists(path + "/" + leader, true); //set watch
                leaderPath = path + "/" + leader;
                data = zk.getData(path + "/" + leader, false, null);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
            if (data != null) {
                break;
            }
        }
        return new LeaderData(leaderPath, data);
    }

    public void clearPrevDoCommitAsLeader(){
        try {
            if(zk.exists(localCommitShardPath + "/doCommit", false) == null){
                zk.delete(localCommitShardPath + "/doCommit", -1);
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public void clearPrevCommitAsLeader() {
        try {
            List<String> children = zk.getChildren(localCommitShardPath, false);
            if (children == null) return;
            for (String znode : children){
                zk.delete(localCommitShardPath + "/" + znode, -1);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prepareCommitAsLeader() {
        try {
            zk.create( localCommitShardPath + "/doCommit", new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prepareCommitAsRegular() {
        while (true) {
            try {
                if (!(zk.exists(localCommitShardPath + "/doCommit", false) ==  null)) break;
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void commit() {
        try {
            zk.create(localCommitShardPath + "/c" + serverId, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                int commitedNodes = zk.getChildren(localCommitShardPath, false).size();
                int liveNodes = zk.getChildren(localLeaderShardPath, false).size();
                if (commitedNodes - 1 == liveNodes)
                    break;
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
