package net.eaglegames.musee.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class RandomUtil {
    private RandomUtil() {
    }

    /**
     * Get a random list of integers from a sequential list
     *
     * @param max          the max range of integers
     * @param randomAmount how big the slice should be
     * @return list of integers taken off the top of a shuffled list of integers
     */
    public static List<Integer> getRandomInts(final int max, final int randomAmount) {
        final List<Integer> numbers = new ArrayList<>(max);
        IntStream.range(0, max).forEach(numbers::add);

        Collections.shuffle(numbers);

        return numbers.subList(0, randomAmount);
    }
}
