package com.tw.game.generator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SolutionGeneratorTest {
    @Test
    public void testCreateSolvedPuzzleGeneratesSolved9X9SudokuPuzzle() throws Exception {
        SolutionGenerator9X9 solutionGenerator = new SolutionGenerator9X9(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        });
        List<List<Integer>> solvedPuzzle = solutionGenerator.createSolvedPuzzle();
        int[][] expected = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {5, 6, 4, 8, 9, 7, 2, 3, 1},
                {8, 9, 7, 2, 3, 1, 5, 6, 4},
                {2, 3, 1, 5, 6, 4, 8, 9, 7},
                {3, 1, 2, 6, 4, 5, 9, 7, 8},
                {6, 4, 5, 9, 7, 8, 3, 1, 2},
                {9, 7, 8, 3, 1, 2, 6, 4, 5}
        };
        for (int i = 0; i < 9; i++) {
            assertEquals(9, solvedPuzzle.get(i).size());
            for (int j = 0; j < 9; j++) {
                assertEquals(expected[i][j], solvedPuzzle.get(i).get(j).intValue());
            }
        }
    }

    @Test
    public void testCreateSolvedPuzzleGeneratesSolved9X9SudokuPuzzleWithRandomNumbers() throws Exception {
        SolutionGenerator9X9 solutionGenerator = new SolutionGenerator9X9(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
            }
        });
        List<List<Integer>> solvedPuzzle = solutionGenerator.createSolvedPuzzle();
        int[][] expected = {
                {9, 1, 8, 2, 3, 5, 7, 4, 6},
                {2, 3, 5, 7, 4, 6, 9, 1, 8},
                {7, 4, 6, 9, 1, 8, 2, 3, 5},
                {3, 5, 2, 4, 6, 7, 1, 8, 9},
                {4, 6, 7, 1, 8, 9, 3, 5, 2},
                {1, 8, 9, 3, 5, 2, 4, 6, 7},
                {8, 9, 1, 5, 2, 3, 6, 7, 4},
                {5, 2, 3, 6, 7, 4, 8, 9, 1},
                {6, 7, 4, 8, 9, 1, 5, 2, 3}
        };
        for (int i = 0; i < 9; i++) {
            assertEquals(9, solvedPuzzle.get(i).size());
            for (int j = 0; j < 9; j++) {
                assertEquals(expected[i][j], solvedPuzzle.get(i).get(j).intValue());
            }
        }
    }
}
