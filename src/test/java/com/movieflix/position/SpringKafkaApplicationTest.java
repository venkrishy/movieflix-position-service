package com.movieflix.position;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.movieflix.position.consumer.PositionConsumer;
import com.movieflix.position.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaApplicationTest {

    @Autowired
    private PositionConsumer positionConsumer;

    @Autowired
    private Sender sender;

//    private CountDownLatch latch = new CountDownLatch(1);

//    public CountDownLatch getLatch() {
//        return latch;
//    }

    @Test
    public void testReceive() throws Exception {
//        sender.sendToPositionTopic("Hello Spring Kafka ABC!");
        //TODO:Add flush

        //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        //assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}