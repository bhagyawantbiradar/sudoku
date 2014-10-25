package com.tw.game.checker;

import com.tw.game.result.Error;
import com.tw.game.result.Result;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SudokuCheckerTest {

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

    @Test
    public void testValidateSolutionGivesResult() throws Exception {
        Checker sudokuChecker = new SudokuChecker();

        List<List<Integer>> solution1 = new ArrayList<>();
        List<List<Integer>> solution2 = new ArrayList<>();
        solution1.add(Arrays.asList(6, 7, 5, 4, 2, 9, 8, 3, 1));
        solution1.add(Arrays.asList(4, 8, 2, 7, 1, 3, 5, 9, 6));
        solution1.add(Arrays.asList(9, 1, 3, 6, 5, 8, 4, 7, 2));
        solution1.add(Arrays.asList(7, 2, 8, 1, 3, 4, 9, 6, 5));
        solution1.add(Arrays.asList(1, 9, 4, 8, 6, 5, 7, 2, 3));
        solution1.add(Arrays.asList(5, 3, 6, 9, 7, 2, 1, 4, 8));
        solution1.add(Arrays.asList(8, 5, 9, 2, 4, 6, 3, 1, 7));
        solution1.add(Arrays.asList(2, 4, 7, 3, 8, 1, 6, 5, 9));
        solution1.add(Arrays.asList(3, 6, 1, 5, 9, 7, 2, 8, 4));

        solution2.add(Arrays.asList(6, 9, 5, 4, 7, 2, 8, 1, 3));
        solution2.add(Arrays.asList(4, 7, 2, 8, 1, 3, 6, 9, 5));
        solution2.add(Arrays.asList(8, 1, 3, 6, 9, 5, 4, 7, 2));
        solution2.add(Arrays.asList(7, 2, 4, 1, 3, 8, 9, 5, 6));
        solution2.add(Arrays.asList(1, 3, 8, 9, 5, 6, 7, 2, 4));
        solution2.add(Arrays.asList(9, 5, 6, 7, 2, 4, 1, 3, 8));
        solution2.add(Arrays.asList(5, 6, 9, 2, 4, 7, 3, 8, 1));
        solution2.add(Arrays.asList(2, 4, 7, 3, 8, 1, 5, 6, 9));
        solution2.add(Arrays.asList(3, 8, 1, 5, 6, 9, 2, 4, 7));

        Result result1 = sudokuChecker.validateSolution(solution1);
        Result result2 = sudokuChecker.validateSolution(solution2);

        assertEquals(true, result1.isCorrect());
        assertEquals(true, result2.isCorrect());
    }
}
