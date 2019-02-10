package com.movieflix.position;

import java.util.Random;

public class Utils {

    public static int random(int lower, int upper) {
        Random r = new Random();
        int[] fiveRandomNumbers = r.ints(5, 0, 11).toArray();
        int randomNumber = r.ints(1, 0, 11).findFirst().getAsInt();
        return randomNumber;
    }
}
