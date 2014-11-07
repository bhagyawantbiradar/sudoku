package com.tw.game.solver;

import com.tw.game.checker.SolutionChecker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class SudokuSolverTest {

    private List<List<Integer>> solvedPuzzle;
    private List<List<Integer>> puzzle;


    @Test
    public void shouldBeAbleToSolveGivenPuzzle() {

        solvedPuzzle = new ArrayList<>();
        solvedPuzzle.add(Arrays.asList(6, 7, 5, 4, 2, 9, 8, 3, 1));
        solvedPuzzle.add(Arrays.asList(4, 8, 2, 7, 1, 3, 5, 9, 6));
        solvedPuzzle.add(Arrays.asList(9, 1, 3, 6, 5, 8, 4, 7, 2));
        solvedPuzzle.add(Arrays.asList(7, 2, 8, 1, 3, 4, 9, 6, 5));
        solvedPuzzle.add(Arrays.asList(1, 9, 4, 8, 6, 5, 7, 2, 3));
        solvedPuzzle.add(Arrays.asList(5, 3, 6, 9, 7, 2, 1, 4, 8));
        solvedPuzzle.add(Arrays.asList(8, 5, 9, 2, 4, 6, 3, 1, 7));
        solvedPuzzle.add(Arrays.asList(2, 4, 7, 3, 8, 1, 6, 5, 9));
        solvedPuzzle.add(Arrays.asList(3, 6, 1, 5, 9, 7, 2, 8, 4));

        puzzle = new ArrayList<>();
        puzzle.add(Arrays.asList(0, 0, 5, 4, 2, 0, 8, 3, 1));
        puzzle.add(Arrays.asList(4, 8, 2, 7, 0, 0, 5, 9, 6));
        puzzle.add(Arrays.asList(0, 1, 3, 0, 5, 8, 0, 7, 2));
        puzzle.add(Arrays.asList(0, 2, 0, 1, 3, 0, 9, 6, 5));
        puzzle.add(Arrays.asList(1, 9, 4, 0, 0, 5, 0, 2, 3));
        puzzle.add(Arrays.asList(5, 0, 6, 9, 7, 0, 1, 4, 0));
        puzzle.add(Arrays.asList(8, 5, 0, 2, 4, 6, 0, 1, 7));
        puzzle.add(Arrays.asList(2, 0, 7, 0, 8, 0, 6, 5, 0));
        puzzle.add(Arrays.asList(3, 0, 1, 5, 0, 7, 2, 0, 4));


        SudokuSolver sudokuSolver = new SudokuSolver(new SolutionChecker());
        List<List<Integer>> actual = sudokuSolver.solvePuzzle(this.puzzle);
        for (int i = 0; i < 9; i++) {
            assertEquals(9, actual.get(i).size());
            for (int j = 0; j < 9; j++) {
                Assert.assertEquals(solvedPuzzle.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void shouldBeAbleToSolveGivenDifficultPuzzle() {

        solvedPuzzle = new ArrayList<>();
        solvedPuzzle.add(Arrays.asList(2, 4, 6, 9, 1, 7, 5, 8, 3));
        solvedPuzzle.add(Arrays.asList(9, 7, 5, 3, 8, 4, 1, 6, 2));
        solvedPuzzle.add(Arrays.asList(1, 8, 3, 6, 5, 2, 4, 9, 7));
        solvedPuzzle.add(Arrays.asList(4, 9, 1, 8, 2, 5, 7, 3, 6));
        solvedPuzzle.add(Arrays.asList(7, 5, 8, 4, 3, 6, 9, 2, 1));
        solvedPuzzle.add(Arrays.asList(6, 3, 2, 7, 9, 1, 8, 4, 5));
        solvedPuzzle.add(Arrays.asList(5, 6, 4, 2, 7, 8, 3, 1, 9));
        solvedPuzzle.add(Arrays.asList(8, 1, 9, 5, 6, 3, 2, 7, 4));
        solvedPuzzle.add(Arrays.asList(3, 2, 7, 1, 4, 9, 6, 5, 8));

        puzzle = new ArrayList<>();
        puzzle.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 8, 0));
        puzzle.add(Arrays.asList(0, 7, 5, 3, 0, 4, 1, 0, 0));
        puzzle.add(Arrays.asList(0, 8, 0, 0, 5, 2, 0, 9, 0));
        puzzle.add(Arrays.asList(4, 0, 1, 0, 0, 5, 0, 3, 0));
        puzzle.add(Arrays.asList(0, 0, 8, 0, 0, 0, 9, 0, 0));
        puzzle.add(Arrays.asList(6, 0, 0, 7, 0, 0, 0, 0, 0));
        puzzle.add(Arrays.asList(5, 6, 0, 0, 0, 0, 0, 0, 9));
        puzzle.add(Arrays.asList(0, 0, 0, 0, 0, 3, 0, 0, 0));
        puzzle.add(Arrays.asList(0, 0, 0, 1, 4, 0, 0, 0, 0));


        SudokuSolver sudokuSolver = new SudokuSolver(new SolutionChecker());
        List<List<Integer>> actual = sudokuSolver.solvePuzzle(this.puzzle);
        for (int i = 0; i < 9; i++) {
            assertEquals(9, actual.get(i).size());
            for (int j = 0; j < 9; j++) {
                Assert.assertEquals(solvedPuzzle.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void shouldBeAbleToSolveGivenVeryDifficultPuzzle() {

        solvedPuzzle = new ArrayList<>();
        solvedPuzzle.add(Arrays.asList(6, 4, 9, 8, 2, 3, 1, 5, 7));
        solvedPuzzle.add(Arrays.asList(3, 8, 7, 1, 5, 4, 9, 2, 6));
        solvedPuzzle.add(Arrays.asList(5, 2, 1, 6, 9, 7, 4, 3, 8));
        solvedPuzzle.add(Arrays.asList(4, 9, 6, 5, 7, 2, 8, 1, 3));
        solvedPuzzle.add(Arrays.asList(2, 5, 3, 4, 1, 8, 6, 7, 9));
        solvedPuzzle.add(Arrays.asList(7, 1, 8, 9, 3, 6, 5, 4, 2));
        solvedPuzzle.add(Arrays.asList(9, 3, 4, 7, 6, 5, 2, 8, 1));
        solvedPuzzle.add(Arrays.asList(1, 7, 5, 2, 8, 9, 3, 6, 4));
        solvedPuzzle.add(Arrays.asList(8, 6, 2, 3, 4, 1, 7, 9, 5));

        puzzle = new ArrayList<>();
        puzzle.add(Arrays.asList(0, 4, 0, 8, 0, 0, 0, 0, 0));
        puzzle.add(Arrays.asList(0, 0, 0, 1, 5, 0, 0, 0, 0));
        puzzle.add(Arrays.asList(5, 0, 0, 0, 9, 7, 0, 0, 0));
        puzzle.add(Arrays.asList(0, 0, 0, 0, 7, 0, 8, 0, 3));
        puzzle.add(Arrays.asList(0, 0, 0, 4, 0, 8, 6, 0, 0));
        puzzle.add(Arrays.asList(7, 0, 8, 9, 3, 0, 0, 0, 0));
        puzzle.add(Arrays.asList(9, 0, 0, 0, 0, 0, 2, 0, 1));
        puzzle.add(Arrays.asList(0, 7, 0, 0, 0, 0, 0, 6, 4));
        puzzle.add(Arrays.asList(0, 0, 0, 3, 4, 0, 0, 9, 0));

        SudokuSolver sudokuSolver = new SudokuSolver(new SolutionChecker());
        List<List<Integer>> actual = sudokuSolver.solvePuzzle(this.puzzle);
        for (int i = 0; i < 9; i++) {
            assertEquals(9, actual.get(i).size());
            for (int j = 0; j < 9; j++) {
                Assert.assertEquals(solvedPuzzle.get(i).get(j), actual.get(i).get(j));
            }
        }
    }
}
