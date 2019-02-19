package com.movieflix.position.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.movieflix.position.Employees;
import com.movieflix.position.InMemoryDatabase;

@Data
@Slf4j
public class EmployeeConsumer implements ConsumerSeekAware {

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    private ThreadLocal<ConsumerSeekCallback> seekCallBack = new ThreadLocal<>();

    public void registerSeekCallback(ConsumerSeekCallback consumerSeekCallback) {
        this.seekCallBack.set(consumerSeekCallback);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback consumerSeekCallback) {
        assignments.forEach((t, o) -> consumerSeekCallback.seekToBeginning(t.topic(), t.partition()));
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {
        //this.seekCallBack.set(consumerSeekCallback);
    }

    @KafkaListener(topics = "${kafka.topic.employee}", containerFactory = "kafkaListenerContainerFactoryEmployee")
    public void consumeFromPositionTopic(@Payload Employees employee,
                                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String  key,
                                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        inMemoryDatabase.getEmployees().add(employee);
        log.info("received employee='{}'", employee);
    }

}