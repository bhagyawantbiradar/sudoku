package com.tw.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RandomNumbersGenerator {
    public List<Integer> getRandomNumbers() {
        List<Integer> randomNumbers = new ArrayList<Integer>();
        randomNumbers.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(randomNumbers);
        return randomNumbers;
    }
}
