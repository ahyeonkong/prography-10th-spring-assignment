package assignment.game.PingPong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class PingPongApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingPongApplication.class, args);
	}

}

