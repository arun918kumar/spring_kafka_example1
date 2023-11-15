package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

}