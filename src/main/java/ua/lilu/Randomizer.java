package ua.lilu;

import java.util.Random;

/**
 * Created by Lilu on 08.12.2016.
 */
public class Randomizer {
    private static Random rnd = new Random();

    public static String generateRandomNumbers(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    public static int generateRandomNumber(int len) {
        return rnd.nextInt(len);
    }
}
