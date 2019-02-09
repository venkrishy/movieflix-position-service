package com.movieflix.position;


import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Collections.singletonMap("response", "hello world");
    }
}
