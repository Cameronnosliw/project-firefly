package com.example.spoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.spoon.mappers")
public class SpoonprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpoonprojectApplication.class, args);
    }

}
