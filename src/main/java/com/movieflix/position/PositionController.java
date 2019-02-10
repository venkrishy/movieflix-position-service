package com.movieflix.position;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.position.consumer.Receiver;
import com.movieflix.position.producer.Sender;

@RestController
public class PositionController {

    @Autowired
    Sender sender;

    @Autowired
    Receiver receiver;

    @GetMapping("/")
    public Map<String, List<String>> hello() {
        sender.sendToPositionTopic("My Name is Billa");
        sender.sendToPositionTopic("Vazhkai Ellam");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        return Collections.singletonMap("response", receiver.getList());
    }
}
