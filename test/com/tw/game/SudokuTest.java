package com.tw.game;

import com.tw.game.checker.Checker;
import com.tw.game.checker.SolutionChecker;
import com.tw.game.factory.Factory;
import com.tw.game.generator.NumberGenerator;
import com.tw.game.generator.SolutionGenerator;
import com.tw.game.generator.SolutionGenerator9X9;
import com.tw.game.result.Result;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SudokuTest {

    private class SudokuFactoryStub implements Factory {

        @Override
        public SolutionGenerator getSolutionGenerator() {
            return new SolutionGenerator9X9(new NumberGenerator() {
                @Override
                public List<Integer> getNumbers() {
                    return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
                }
            });
        }

        @Override
        public Checker getSolutionChecker() {
            return new Checker() {
                @Override
                public Result validateSolution(List<List<Integer>> puzzle) {
                    return null;
                }
            };
        }

        @Override
        public NumberGenerator getRandomNumberGenerator() {
            return new NumberGenerator() {
                @Override
                public List<Integer> getNumbers() {
                    return Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6);
                }
            };
        }
    }

    @Test
    public void testGeneratePuzzleCreatesAValidPuzzleFromSolution() throws Exception {
        Sudoku sudoku = new Sudoku(new SudokuFactoryStub());
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
    public void testSudokuValidatesTheSolution() throws Exception {
        Sudoku sudoku = new Sudoku(new SudokuFactoryStub());
        List<List<Integer>> solution = new ArrayList<>();
        Result result = sudoku.validateSolution(solution);
        assertNull(result);
    }
}