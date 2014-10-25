package com.tw.game.generator;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RandomNumbersGeneratorTest {

    @Test
    public void testRandomNumbersGeneratorGeneratesListContainingAllNumbers1To9WithoutDuplicateValues() throws Exception {
        List<Integer> randomNumbers = new RandomNumbersGenerator().getNumbers();
        Collections.sort(randomNumbers);
        Set<Integer> uniqueRandomNumbers = new HashSet<Integer>(randomNumbers);
        assertEquals(9, randomNumbers.size());
        assertEquals(randomNumbers.size(), uniqueRandomNumbers.size());
        Object[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(numbers, randomNumbers.toArray());
    }

    @Test
    public void testRandomNumbersGenerator() throws Exception {
        List<Integer> randomNumbers1 = new RandomNumbersGenerator().getNumbers();
        List<Integer> randomNumbers2 = new RandomNumbersGenerator().getNumbers();

        assertNotSame(randomNumbers1, randomNumbers2);
    }
}