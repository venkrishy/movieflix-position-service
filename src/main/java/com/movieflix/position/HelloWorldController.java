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
public class HelloWorldController {

    @Autowired
    private Sender send;

    @GetMapping("/hello")
    public Map<String, String> hello() throws Exception {
        log.info("Hello World Invoked");
        return Collections.singletonMap("response", "hello world");
    }

    @GetMapping("/")
    public Map<String, String> root() throws Exception {
        log.info("Root Invoked");
        return Collections.singletonMap("response", "Root URL Invoked");
    }
}
