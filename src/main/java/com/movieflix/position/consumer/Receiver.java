package com.movieflix.position.consumer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@Data
public class Receiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);
    List<String> list = new ArrayList<>();
    @KafkaListener(topics = "z3l1kzly-default")

    public void receive(String payload) {
        if (list.size() > 100) {
            list.clear();
        }
        list.add(payload);
        LOGGER.info("received payload='{}'", payload);
    }
}