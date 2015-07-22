package com.samsung.microservice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.microservice.UserRegistrationConfiguration;

@SpringBootApplication
public class UserRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationConfiguration.class, args);
    }
}
