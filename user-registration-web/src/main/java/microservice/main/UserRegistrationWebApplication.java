package microservice.main;

import microservice.UserRegistrationWebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserRegistrationWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationWebConfiguration.class, args);
    }
}
