package DS.app;

import DS.zookeeper.ZKClientImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import protos.VoteServiceImpl;

import java.io.IOException;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World";
	}

	public static void main(String[] args) {
	    String zk_IP = "172.18.0." + args[1] + ":2181";
		ZKClientImpl zkClient = new ZKClientImpl(zk_IP, Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		Server server = ServerBuilder
				.forPort(8081)
				.addService(new VoteServiceImpl(zkClient)).build();

		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.awaitTermination();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//SpringApplication.run(Application.class, args);
//
//		if (zkClient.getLocalLeader() == zkClient.serverId){
//            System.out.println("clearing previous DoCommit");
//            zkClient.clearPrevDoCommitAsLeader();
//
//            // send addVote and wait for ok from everyone
//
//            System.out.println("clearing previous commit");
//            zkClient.clearPrevCommitAsLeader();
//            System.out.println("creating doCommit");
//            zkClient.prepareCommitAsLeader();
//            System.out.println("commiting");
//            zkClient.commit();
//            System.out.println("FINISHED COMMIT");
//        }
//        else{
//            // get addVote from leader and send ok
//            System.out.println("waiting for doCommit");
//            zkClient.prepareCommitAsRegular(); // wait for doCommit
//
//            // update vote in database
//            System.out.println("commiting");
//            zkClient.commit();
//            System.out.println("FINISHED COMMIT");
//        }

	}

}
