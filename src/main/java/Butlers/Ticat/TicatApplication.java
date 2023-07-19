package Butlers.Ticat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TicatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicatApplication.class, args);
	}

}
