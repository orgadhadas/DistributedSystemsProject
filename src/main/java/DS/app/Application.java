package DS.app;

import DS.zookeeper.ZKClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World";
	}

	public static void main(String[] args) {
		ZKClientImpl zkClient = new ZKClientImpl("172.18.0.100:2181", Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		SpringApplication.run(Application.class, args);
	}

}
