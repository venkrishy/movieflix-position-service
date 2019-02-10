package com.movieflix.position.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import com.movieflix.position.InMemoryDatabase;
import com.movieflix.position.Position;

@Data
@Slf4j
public class PositionConsumer {

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @KafkaListener(topics = "${kafka.topic.position}")
    public void consumeFromPositionTopic(@Payload Position position,
                        @Headers MessageHeaders headers) {
        inMemoryDatabase.getPositions().add(position);
        log.info("received position='{}'", position);
    }
}