package com.movieflix.position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class InMemoryDatabase {
    List<Position> positions = new ArrayList<>();

    List<Recommendation> recommendations = new ArrayList<>();

    List<Title> titles = Arrays.asList(
            new Title("The Hobbit: The Battle of the Five Armies"),
            new Title("Mission: Impossible"),
            new Title("A Quiet Place"),
            new Title("The Favourite"),
            new Title("Now You See Me 2"),
            new Title("Reign of the Supermen"),
            new Title("A Simple Favor"),
            new Title("Split"),
            new Title("Avengers: Infinity War"),
            new Title("Harry Potter and the Philosopher's Stone"),
            new Title("Schindler's List"),
            new Title("Speed Kills")
    );

    List<String> users = Arrays.asList(
            "Venky",
            "Bob",
            "Eric",
            "Jason",
            "Dan",
            "Dick",
            "Richard",
            "Justin",
            "Grant",
            "Maloney",
            "Tony",
            "Steve"
    );

    public List<String> getUsers() {
        return users;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
}
