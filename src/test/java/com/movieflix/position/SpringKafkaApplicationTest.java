package com.movieflix.position;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.movieflix.position.consumer.Receiver;
import com.movieflix.position.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaApplicationTest {

    @Autowired
    private Receiver receiver;

    @Autowired
    private Sender sender;


    @Test
    public void testReceive() throws Exception {
        sender.send("Hello Spring Kafka ABC!");
        //TODO:Add flush

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        //assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}