package com.tw.game.solver;

import com.tw.game.checker.SolutionChecker;
import com.tw.game.result.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuSolver {

    private SolutionChecker solutionChecker;

    public SudokuSolver(SolutionChecker solutionChecker) {
        this.solutionChecker = solutionChecker;
    }

    public boolean solve(Cell cur,List<List<Integer>> grid) {
        if (cur == null)    return true;
        if (grid.get(cur.getRow()).get(cur.getColumn()) != 0)   return solve(getNextCell(cur),grid);
        for (int i = 1; i <= 9; i++) {
            boolean valid = solutionChecker.isNumberValid(grid, cur, i);
            if (!valid) continue;
            grid.get(cur.getRow()).set(cur.getColumn(),i);
            boolean solved = solve(getNextCell(cur),grid);
            if (solved) return true;
            else    grid.get(cur.getRow()).set(cur.getColumn(),0);
        }
        return false;
    }

    public List<List<Integer>> solvePuzzle(List<List<Integer>> puzzle) {
        List<List<Integer>> copiedPuzzle = copy(puzzle);
        solve(new Cell(0, 0),copiedPuzzle);
        return copiedPuzzle;
    }

    private List<List<Integer>> copy(List<List<Integer>> puzzle){
        List<List<Integer>> copiedPuzzle = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> destination = Arrays.asList(new Integer[9]);
            Collections.copy(destination, puzzle.get(i));
            copiedPuzzle.add(destination);
        }
        return copiedPuzzle;
    }

    private Cell getNextCell(Cell cur) {
        int row = cur.getRow();
        int col = cur.getColumn();
        col++;
        if (col > 8) {
            col = 0;
            row++;
        }
        if (row > 8)    return null;
        return new Cell(row, col);
    }
}