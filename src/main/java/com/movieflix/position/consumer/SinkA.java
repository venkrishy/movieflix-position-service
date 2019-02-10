package com.movieflix.position.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SinkA {
    String RECOMMENDATION_CONSUMER = "recommendationConsumer";

    @Input("recommendationConsumer")
    SubscribableChannel recommendationConsumer();
}