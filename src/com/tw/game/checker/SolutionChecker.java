package com.tw.game.checker;

import com.tw.game.result.Error;
import com.tw.game.result.Result;

import java.util.List;
import java.util.TreeSet;

public class SolutionChecker implements Checker {

    @Override
    public Result validateSolution(List<List<Integer>> puzzle) {
        Result result = new Result();
        for (int i = 0; i < 9; i++) {
            TreeSet<Integer> uniqueRow = new TreeSet<>();
            TreeSet<Integer> uniqueColumn = new TreeSet<>();
            for (int j = 0; j < 9; j++)
                if (!uniqueRow.add(puzzle.get(i).get(j)))
                    result.addError(new Error(i, j));
                else detectDuplicateValue(puzzle, result, j, uniqueColumn, i);
        }
        for (int i = 0; i < 9; i++) {
            TreeSet<Integer> uniqueBlock1 = new TreeSet<>();
            TreeSet<Integer> uniqueBlock2 = new TreeSet<>();
            TreeSet<Integer> uniqueBlock3 = new TreeSet<>();
            checkFor(puzzle, result, i++, uniqueBlock1, uniqueBlock2, uniqueBlock3);
            checkFor(puzzle, result, i++, uniqueBlock1, uniqueBlock2, uniqueBlock3);
            checkFor(puzzle, result, i, uniqueBlock1, uniqueBlock2, uniqueBlock3);
        }
        return result;
    }

    private void checkFor(List<List<Integer>> puzzle, Result result, int i, TreeSet<Integer> uniqueBlock1, TreeSet<Integer> uniqueBlock2, TreeSet<Integer> uniqueBlock3) {
        for (int j = 0; j < 9; j++) {
            int quotient = j / 3;
            if (quotient == 0)  detectDuplicateValue(puzzle, result, i, uniqueBlock1, j);
            if (quotient == 1)  detectDuplicateValue(puzzle, result, i, uniqueBlock2, j);
            if (quotient == 2)  detectDuplicateValue(puzzle, result, i, uniqueBlock3, j);
        }
    }

    private void detectDuplicateValue(List<List<Integer>> puzzle, Result result, int i, TreeSet<Integer> uniqueBlock1, int j) {
        if (!uniqueBlock1.add(puzzle.get(i).get(j)))    result.addError(new Error(i, j));
    }
}