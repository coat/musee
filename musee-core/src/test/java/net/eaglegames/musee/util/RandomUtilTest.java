package net.eaglegames.musee.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RandomUtilTest {
    @Test
    public void randomIntReturnsInts() {
        int max = 10;
        int randomAmount = 5;

        List<Integer> randomInts = RandomUtil.getRandomInts(max, randomAmount);

        assertEquals(randomAmount, randomInts.size());
    }
}
