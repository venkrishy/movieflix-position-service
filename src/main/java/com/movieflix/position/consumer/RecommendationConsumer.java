package com.movieflix.position.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.movieflix.position.InMemoryDatabase;
import com.movieflix.position.Trend;

@Data
@Slf4j
public class RecommendationConsumer {

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @KafkaListener(topics = "${kafka.topic.recommendation}", containerFactory = "kafkaListenerContainerFactoryStringLong")
    public void consumeFromRecommendationTopic(@Payload Long count,
                                               @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String  key,
                                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        Trend trend = new Trend(key, count);
        inMemoryDatabase.getTrends().add(trend);
        log.info("received Recommendation='{}'", trend);
    }
}