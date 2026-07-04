package cm.pm.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		// 1. Force the JVM to run in UTC timezone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// 2. Start the Spring Boot application
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}