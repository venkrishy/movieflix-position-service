package com.movieflix.position;


import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/hello")
    public Map<String, String> hello() throws Exception {
        kafkaService.run();
        return Collections.singletonMap("response", "hello world");
    }
}
