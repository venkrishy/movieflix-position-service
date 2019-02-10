package com.movieflix.position.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SinkB {
    String POSITION_CONSUMER = "positionConsumer";

    @Input("positionConsumer")
    SubscribableChannel positionConsumer();
}