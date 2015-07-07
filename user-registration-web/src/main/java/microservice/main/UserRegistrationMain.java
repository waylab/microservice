package microservice.main;

import microservice.UserRegistrationConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserRegistrationMain {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationConfiguration.class, args);
    }
}
