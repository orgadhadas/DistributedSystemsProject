package DS.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZKClientImpl implements Watcher {
    private ZooKeeper zk;
    public int shardId;
    public int serverId;
    public boolean commited;
    private int localLeader;
    private String localLeaderZnodePath;
    private String globalLeaderZnodePath;

    private String root = "/ELECTIONS";
    private String globalLeaderPath = root + "/leaderGLOBAL";
    private String localLeaderPath = root + "/leaderLOCAL";
    public String localCommitPath = root + "/commitLOCAL";
    private String localLeaderShardPath;
    private String localCommitShardPath;
    public String systemUpZnode = root + "/sysUp";

    private boolean isSystemUp = false;

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
        localCommitShardPath = root + "/commitLOCAL/" + shardId;

        initZK();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case NodeDeleted:
                if (localLeaderZnodePath.equals(watchedEvent.getPath())) {
                    electLocalLeader();
                    if (localLeader == serverId) {
                        signToGlobalLeaders();
                    }
                }
                break;
            case NodeDataChanged:
                if ((localCommitShardPath + "/doCommit").equals(watchedEvent.getPath())) {
                    setCommitFlag(true);
                }
                if (systemUpZnode.equals(watchedEvent.getPath())){
                    boolean b = getSystemUp();
                    setSystemUp(!b);
                    try {
                        zk.getData(systemUpZnode, true, null);
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("changed system up to " + getSystemUp());
                }
                break;
        }
    }

    public int getLocalLeader() {
        return localLeader;
    }


    private void initZK() {
        createIfDoesntExist(root);
        createIfDoesntExist(globalLeaderPath);
        createIfDoesntExist(localLeaderPath);
        createIfDoesntExist(localLeaderShardPath);
        createIfDoesntExist(localCommitPath);
        createIfDoesntExist(localCommitShardPath);
        createIfDoesntExist(localCommitShardPath + "/doCommit");
        createIfDoesntExist(systemUpZnode);

        commit(); // initialize commit znodes in barrier

        signToLocalLeaders();
        electLocalLeader();
        if (localLeader == serverId) {
            signToGlobalLeaders();
        }

        //wait for system to start - put watch
        try {
            zk.getData(systemUpZnode, true, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createIfDoesntExist(String path) {
        try {
            zk.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        catch (KeeperException.NodeExistsException e){}
        catch (InterruptedException | KeeperException e) {
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

    public void clearPrevCommitAsLeader() {
        try {
            List<String> children = zk.getChildren(localCommitShardPath, false);
            if (children == null) return;
            for (String znode : children) {
                if (znode.equals("doCommit")) continue;
                zk.delete(localCommitShardPath + "/" + znode, -1);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prepareCommitAsLeader() {
        try {
            zk.setData(localCommitShardPath + "/doCommit",  new byte[]{}, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prepareCommitAsRegular() {
        try {
            zk.getData(localCommitShardPath + "/doCommit", true, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            zk.create(localCommitShardPath + "/c" + serverId, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForCommitBerrier(){

        while (true) {
            try {
                int commitedNodes = zk.getChildren(localCommitShardPath, false).size();
                int liveNodes = zk.getChildren(localLeaderShardPath, false).size();
                if (commitedNodes - 1 == liveNodes) // we do not count the doCommit znode
                    break;
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> getLiveLocalServers() {
        List<Integer> result = new ArrayList<>();
        try {
            for (String node : zk.getChildren(localLeaderShardPath, false)) {
                byte[] data = zk.getData(localLeaderShardPath + "/" + node, false, null);
                result.add(Integer.parseInt(new String(data)));
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Integer> getAllLocalLeaders() {
        List<Integer> result = new ArrayList<>();
        try {
            for (String node : zk.getChildren(globalLeaderPath, false)) {
                byte[] data = zk.getData(globalLeaderPath + "/" + node, false, null);
                result.add(Integer.parseInt(new String(data)));
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean getCommitFlag(){
        synchronized (this){
            return this.commited;
        }
    }
    public void setCommitFlag(boolean b){
        synchronized (this){
            this.commited = b;
        }
    }

    private void setSystemUp(boolean b){
        synchronized (this){
            this.isSystemUp = b;
        }
    }

    public boolean getSystemUp(){
        synchronized (this){
            return this.isSystemUp;
        }
    }

    public void triggerSystemUpZnode(){
        try {
            zk.setData(systemUpZnode,  new byte[]{}, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
