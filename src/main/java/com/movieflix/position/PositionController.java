package com.movieflix.position;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.position.consumer.PositionConsumer;
import com.movieflix.position.producer.Sender;

@RestController
@Slf4j
public class PositionController {

    @Autowired
    Sender sender;

    @Autowired
    PositionConsumer positionConsumer;

    @Autowired
    InMemoryDatabase inMemoryDatabase;


    @GetMapping("/")
    public Map<String, String> hello() {
        return Collections.singletonMap("response", "Hello World");
    }

    @GetMapping("/position/produce")
    public Map<String, Object> producePositions() {
        List<String> users = inMemoryDatabase.getUsers();
        List<Title> titles = inMemoryDatabase.getTitles();
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Position> positions = new ArrayList<>();
        for (int iCnt = 0; iCnt < 10; iCnt++) {
            int randMovie = Utils.random(0, titles.size() - 1);
            int randomPosition = Utils.random(0, 120);
            int userId = Utils.random(0, users.size() - 1);

            String user = users.get(userId);
            String title = titles.get(randMovie).getTitle();
            log.info("User {} Watched {} at position {}", user, title, randomPosition);
            Position position = new Position(user, title, randomPosition);
            positions.add(position);
            sender.sendToPositionTopic(user, position);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        ret.put("comment", "Random User watched a random title and stopped watching at a random position");
        ret.put("positions", positions);
        return ret;
    }

    @GetMapping("/users")
    public Map<String, List<String>> getUsers() {
        Map<String, List<String>> ret = new HashMap<>();
        List<String> users = inMemoryDatabase.getUsers();
        ret.put("users", users);
        return ret;
    }

    @GetMapping("/titles")
    public Map<String, List<Title>> getTitles() {
        Map<String, List<Title>> ret = new HashMap<>();
        List<Title> titles = inMemoryDatabase.getTitles();
        ret.put("titles", titles);
        return ret;
    }

}
