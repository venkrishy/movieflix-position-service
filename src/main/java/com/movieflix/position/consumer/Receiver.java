package com.movieflix.position.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Receiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

//    private CountDownLatch latch = new CountDownLatch(1);

//    public CountDownLatch getLatch() {
//        return latch;
//    }

    @KafkaListener(topics = "first_topic")
    public void receive(ConsumerRecord<?, ?> payload) {
        //TODO: Update database
        LOGGER.info("received payload='{}'", payload.value());
//        latch.countDown();
    }

}