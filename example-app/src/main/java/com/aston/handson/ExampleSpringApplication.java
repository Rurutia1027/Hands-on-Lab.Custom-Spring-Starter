package com.aston.handson;

import com.aston.handson.enable.EnableS3Starter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableS3Starter
@SpringBootApplication
public class ExampleSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringApplication.class);
    }
}