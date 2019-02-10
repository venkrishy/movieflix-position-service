package com.movieflix.position;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@Slf4j
@EnableKafka
public class PositionApplication {

    public static void main(String[] args) {
            SpringApplication.run(PositionApplication.class, args);
    }

}

