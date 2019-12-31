package DS.app;

import DS.elections.Elections;
import DS.grpcServer.VoteServiceImpl;
import DS.zookeeper.ZKClientImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	    Elections elections = new Elections(Integer.parseInt(args[2]));
		ZKClientImpl zkClient = new ZKClientImpl(zk_IP, Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		Server server = ServerBuilder
				.forPort(8081)
				.addService(new VoteServiceImpl(zkClient, elections)).build();

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

	}

}
