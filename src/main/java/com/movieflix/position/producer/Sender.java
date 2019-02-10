package com.movieflix.position.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.movieflix.position.Position;

public class Sender {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, Position> kafkaTemplate;

    @Value("${kafka.topic.position}")
    private String positionTopic;

    public void sendToPositionTopic(String userId, Position position) {
        LOGGER.info("sending payload='{}'", position);
        Message<Position> message = MessageBuilder
                .withPayload(position)
                .setHeader(KafkaHeaders.MESSAGE_KEY, userId)
                .setHeader(KafkaHeaders.TOPIC, positionTopic)
                .build();

        kafkaTemplate.send(message);

        //                .setHeader(JsonSerializer.ADD_TYPE_INFO_HEADERS, false)
    }
}