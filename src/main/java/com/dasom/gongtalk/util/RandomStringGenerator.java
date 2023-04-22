package com.dasom.gongtalk.util;

import java.util.Random;

public class RandomStringGenerator {

    private int a = 97; // letter 'a'
    private int z = 122; // letter 'z'
    private Random random = new Random();

    public String generate(int length, int numLength) {
        int wordLength = length - numLength;
        StringBuilder buffer = new StringBuilder(wordLength);

        for (int i = 0; i < wordLength; i++) {
            int randomLimitedInt = a + (int)
                    (random.nextFloat() * (z - a + 1));
            buffer.append((char) randomLimitedInt);
        }

        if (numLength > 0) {
            int upperBound = (int) Math.pow(10, numLength);
            int lowerBound = (int) Math.pow(10, numLength - 1);
            int randomNum = random.nextInt(upperBound - lowerBound) + lowerBound;

            buffer.append(randomNum);
        }

        return buffer.toString();
    }
}
