package com.movieflix.position;


import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.position.producer.Sender;

@RestController
@Slf4j
public class PositionController {

    @Autowired
    private Sender send;

    @GetMapping("/hello")
    public Map<String, String> hello() throws Exception {
        this.send.send("foo1");
        this.send.send("foo2");
        this.send.send("foo3");
        log.info("Three Sent");
        return Collections.singletonMap("response", "hello world");
    }
}
