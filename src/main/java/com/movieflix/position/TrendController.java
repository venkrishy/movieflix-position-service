package com.movieflix.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.position.consumer.TrendKafkaStream;

@RestController
public class TrendController {
    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @Autowired
    TrendKafkaStream trendKafkaStream;

    @GetMapping("/trigger")
    public void trigger() {
        trendKafkaStream.triggerProcessingStream();
    }

    @GetMapping("/trends")
    public List<Trend> getTrends() {
        return inMemoryDatabase.getTrends();
    }

}
