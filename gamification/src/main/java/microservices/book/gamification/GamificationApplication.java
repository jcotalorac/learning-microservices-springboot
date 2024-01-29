package microservices.book.gamification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class GamificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamificationApplication.class, args);
	}

}
