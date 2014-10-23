package com.tw.sudoku;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SudokuTest {

    @Test
    public void testCreateSolvedPuzzleGeneratesSolved9X9SudokuPuzzle() throws Exception {
        Sudoku sudoku = new Sudoku(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        });
        sudoku.createSolvedPuzzle();
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
            for (int j = 0; j < 9; j++) {
                assertEquals(expected[i][j], sudoku.getSolvedPuzzle().get(i).get(j).intValue());
            }
        }
    }

    @Test
    public void testCreateSolvedPuzzleGeneratesSolved9X9SudokuPuzzleWithRandomNumbers() throws Exception {
        Sudoku sudoku = new Sudoku(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
            }
        });
        sudoku.createSolvedPuzzle();
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
            for (int j = 0; j < 9; j++) {
                assertEquals(expected[i][j], sudoku.getSolvedPuzzle().get(i).get(j).intValue());
            }
        }
    }

    @Test
    public void testGeneratePuzzleCreatesAValidPuzzleFromSolution() throws Exception {
        Sudoku sudoku = new Sudoku(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
            }
        });
        sudoku.createSolvedPuzzle();
        sudoku.generatePuzzle();
        List<List<Integer>> puzzle = sudoku.getPuzzle();

        List<List<Integer>> expected = new ArrayList<>();
        int[][] values = {{9, 4, 6}, {2, 1, 8}, {7, 3, 5}, {3, 8, 9}, {4, 5, 2}, {1, 6, 7}, {8, 7, 4}, {5, 9, 1}, {6, 2, 3}};
        for (int i = 0; i < 9; i++) {
            expected.add(Arrays.asList(new Integer[9]));
            expected.get(i).set(0, values[i][0]);
            expected.get(i).set(7, values[i][1]);
            expected.get(i).set(8, values[i][2]);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(expected.get(i).get(j), puzzle.get(i).get(j));
            }
        }
    }

    @Test
    public void testIsUserSolutionRightGivesTrueIfSolutionIsRight() throws Exception {
        Sudoku sudoku = new Sudoku(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
            }
        });
        sudoku.createSolvedPuzzle();

        List<List<Integer>> rightSolution = new ArrayList<>();
        rightSolution.add(Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6));
        rightSolution.add(Arrays.asList(2, 3, 5, 7, 4, 6, 9, 1, 8));
        rightSolution.add(Arrays.asList(7, 4, 6, 9, 1, 8, 2, 3, 5));
        rightSolution.add(Arrays.asList(3, 5, 2, 4, 6, 7, 1, 8, 9));
        rightSolution.add(Arrays.asList(4, 6, 7, 1, 8, 9, 3, 5, 2));
        rightSolution.add(Arrays.asList(1, 8, 9, 3, 5, 2, 4, 6, 7));
        rightSolution.add(Arrays.asList(8, 9, 1, 5, 2, 3, 6, 7, 4));
        rightSolution.add(Arrays.asList(5, 2, 3, 6, 7, 4, 8, 9, 1));
        rightSolution.add(Arrays.asList(6, 7, 4, 8, 9, 1, 5, 2, 3));

        assertEquals(true, sudoku.isUserSolutionRight(rightSolution));
    }

    @Test
    public void testIsUserSolutionRightGivesFalseIfSolutionIsWrong() throws Exception {
        Sudoku sudoku = new Sudoku(new NumberGenerator() {
            @Override
            public List<Integer> getNumbers() {
                return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
            }
        });
        sudoku.createSolvedPuzzle();

        List<List<Integer>> wrongSolution = new ArrayList<>();
        wrongSolution.add(Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 9));  //wrong entry 9
        wrongSolution.add(Arrays.asList(2, 3, 5, 7, 4, 6, 9, 1, 8));
        wrongSolution.add(Arrays.asList(7, 4, 6, 9, 1, 8, 2, 3, 5));
        wrongSolution.add(Arrays.asList(3, 5, 2, 4, 6, 7, 1, 8, 9));
        wrongSolution.add(Arrays.asList(4, 6, 7, 1, 8, 9, 3, 5, 2));
        wrongSolution.add(Arrays.asList(1, 8, 9, 3, 5, 2, 4, 6, 7));
        wrongSolution.add(Arrays.asList(8, 9, 1, 5, 2, 3, 6, 7, 4));
        wrongSolution.add(Arrays.asList(5, 2, 3, 6, 7, 4, 8, 9, 1));
        wrongSolution.add(Arrays.asList(6, 7, 4, 8, 9, 1, 5, 2, 3));

        assertEquals(false, sudoku.isUserSolutionRight(wrongSolution));
    }
}