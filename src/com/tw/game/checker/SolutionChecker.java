package com.tw.game.checker;

import com.tw.game.result.Cell;
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
            for (int j = 0; j < 9; j++) {
                if (puzzle.get(i).get(j) == 0)
                    continue;
                if (!uniqueRow.add(puzzle.get(i).get(j)))
                    result.addError(new Cell(i, j));
                else detectDuplicateValue(puzzle, result, j, uniqueColumn, i);
            }
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

    @Override
    public boolean isNumberValid(List<List<Integer>> grid,Cell cell, Integer value) {
        for (int c = 0; c < 9; c++)
            if (grid.get(cell.getRow()).get(c).equals(value))
                return false;
        for (int r = 0; r < 9; r++)
            if (grid.get(r).get(cell.getColumn()).equals(value))
                return false;
        int x1 = 3 * (cell.getRow() / 3);
        int y1 = 3 * (cell.getColumn() / 3);
        int x2 = x1 + 2;
        int y2 = y1 + 2;
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                if (grid.get(x).get(y).equals(value))
                    return false;
        return true;
    }

    private void checkFor(List<List<Integer>> puzzle, Result result, int i, TreeSet<Integer> uniqueBlock1, TreeSet<Integer> uniqueBlock2, TreeSet<Integer> uniqueBlock3) {
        for (int j = 0; j < 9; j++) {
            int quotient = j / 3;
            if (quotient == 0) detectDuplicateValue(puzzle, result, i, uniqueBlock1, j);
            if (quotient == 1) detectDuplicateValue(puzzle, result, i, uniqueBlock2, j);
            if (quotient == 2) detectDuplicateValue(puzzle, result, i, uniqueBlock3, j);
        }
    }

    private void detectDuplicateValue(List<List<Integer>> puzzle, Result result, int i, TreeSet<Integer> uniqueBlock1, int j) {
        if (!(puzzle.get(i).get(j) == 0) && !uniqueBlock1.add(puzzle.get(i).get(j)))
            result.addError(new Cell(i, j));
    }
}