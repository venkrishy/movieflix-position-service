package com.movieflix.position.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import com.movieflix.position.InMemoryDatabase;
import com.movieflix.position.Recommendation;

@Data
@Slf4j
public class RecommendationConsumer {

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @KafkaListener(topics = "${kafka.topic.recommendation}")
    public void consumeFromRecommendationTopic(@Payload Recommendation recommendation,
                        @Headers MessageHeaders headers) {
        inMemoryDatabase.getRecommendations().add(recommendation);
        log.info("received recommendation='{}'", recommendation);
    }


}