package com.tw.game.checker;

import com.tw.game.checker.Checker;
import com.tw.game.checker.SudokuChecker;
import com.tw.game.result.*;
import com.tw.game.result.Error;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SudokuCheckerTest {
    @Test
    public void testValidateSolutionGivesResultWithoutErrorsWhenSolutionIsRight() throws Exception {
        Checker sudokuChecker = new SudokuChecker();

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

        Result result = sudokuChecker.validateSolution(rightSolution);
        assertEquals(true, result.isCorrect());
    }

    @Test
    public void testValidateSolutionGivesResultWithErrorsWhenSolutionIsWrong() throws Exception {
        Checker sudokuChecker = new SudokuChecker();

        List<List<Integer>> wrongSolution = new ArrayList<>();
        wrongSolution.add(Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 9));
        wrongSolution.add(Arrays.asList(2, 3, 5, 7, 4, 6, 9, 1, 8));
        wrongSolution.add(Arrays.asList(7, 4, 6, 9, 1, 8, 2, 3, 5));
        wrongSolution.add(Arrays.asList(3, 5, 2, 4, 6, 7, 1, 8, 9));
        wrongSolution.add(Arrays.asList(4, 6, 7, 1, 8, 9, 3, 5, 2));
        wrongSolution.add(Arrays.asList(1, 8, 9, 3, 5, 2, 4, 6, 7));
        wrongSolution.add(Arrays.asList(8, 9, 1, 5, 2, 3, 6, 7, 4));
        wrongSolution.add(Arrays.asList(5, 2, 3, 6, 7, 4, 8, 9, 1));
        wrongSolution.add(Arrays.asList(6, 7, 4, 8, 9, 1, 5, 2, 3));

        Result result = sudokuChecker.validateSolution(wrongSolution);

        assertEquals(false, result.isCorrect());
        assertEquals(3, result.getErrors().size());
        assertEquals(new com.tw.game.result.Error(0,8), result.getErrors().get(0));
        assertEquals(new Error(3,8), result.getErrors().get(1));
        assertEquals(new Error(1,6), result.getErrors().get(2));

    }

    @Test
    public void testValidateSolutionGivesResultWithErrorsWhenSolutionIsWrongInBlock() throws Exception {
        Checker sudokuChecker = new SudokuChecker();

        List<List<Integer>> wrongSolution = new ArrayList<>();
        wrongSolution.add(Arrays.asList(9, 1, 8, 2, 3, 5, 7, 4, 6));
        wrongSolution.add(Arrays.asList(2, 3, 5, 7, 4, 6, 9, 1, 8));
        wrongSolution.add(Arrays.asList(7, 4, 6, 9, 1, 8, 2, 3, 5));
        wrongSolution.add(Arrays.asList(3, 5, 2, 4, 6, 7, 1, 8, 9));
        wrongSolution.add(Arrays.asList(4, 6, 3, 1, 8, 9, 3, 5, 2));
        wrongSolution.add(Arrays.asList(1, 8, 9, 3, 5, 2, 4, 6, 7));
        wrongSolution.add(Arrays.asList(8, 9, 1, 5, 2, 3, 6, 7, 4));
        wrongSolution.add(Arrays.asList(5, 2, 3, 6, 7, 4, 8, 9, 1));
        wrongSolution.add(Arrays.asList(6, 7, 4, 8, 9, 1, 9, 2, 3));

        Result result = sudokuChecker.validateSolution(wrongSolution);

        assertEquals(false, result.isCorrect());
        assertEquals(6, result.getErrors().size());
        assertEquals(new Error(7,2), result.getErrors().get(0));
        assertEquals(new Error(4,6), result.getErrors().get(1));
        assertEquals(new Error(8,6), result.getErrors().get(2));
        assertEquals(new Error(8,6), result.getErrors().get(3));
        assertEquals(new Error(4,2), result.getErrors().get(4));
        assertEquals(new Error(8,6), result.getErrors().get(5));
    }
}
