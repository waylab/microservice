package com.samsung.microservice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.microservice.EmailServiceConfiguration;

@SpringBootApplication
public class EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceConfiguration.class, args);
    }
    
}
