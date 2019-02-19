package com.movieflix.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResumingController {

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @GetMapping("/users")
    public Map<String, List<String>> getUsers() {
        Map<String, List<String>> ret = new HashMap<>();
        List<String> users = inMemoryDatabase.getUsers();
        ret.put("users", users);
        return ret;
    }

    @GetMapping("/user/{user}")
    public List<Position> getPosition(@PathVariable String user) {
        List<Position> positionList = new ArrayList<>();
        for (Position position : inMemoryDatabase.getPositions()) {
            if (position.getUser().equals(user) ) {
                positionList.add(position);
            }
        }
        return positionList;
    }

    @GetMapping("/user/{user}/title/{title}")
    public List<Position> getPosition(@PathVariable String user, @PathVariable String title) {
        List<Position> positionList = new ArrayList<>();
        for (Position position : inMemoryDatabase.getPositions()) {
            if (position.getUser().equals(user) && position.getTitle().equals(title)) {
                positionList.add(position);
            }
        }
        return positionList;
    }

}
