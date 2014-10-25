package com.tw.game;

import com.tw.game.checker.Checker;
import com.tw.game.factory.Factory;
import com.tw.game.generator.NumberGenerator;
import com.tw.game.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
    private final ArrayList<ArrayList<Integer>> solvedPuzzle;
    private final Checker checker;
    private List<List<Integer>> puzzle = new ArrayList<>();
    private final NumberGenerator generator;

    public Sudoku(Factory factory) {
        this.generator = factory.getRandomNumberGenerator();
        this.solvedPuzzle = factory.getSolutionGenerator().createSolvedPuzzle();
        this.checker = factory.getSolutionChecker();
    }

    public List<List<Integer>> getPuzzle() {
        return puzzle;
    }

    public void generatePuzzle() {
        for (int i = 0; i < 9; i++) {
            this.puzzle.add(Arrays.asList(new Integer[9]));
            List<Integer> indexes = this.generator.getNumbers();
            for (int j = 0; j < 3; j++)
                this.puzzle.get(i).set(indexes.get(j) - 1, this.solvedPuzzle.get(i).get(indexes.get(j) - 1));
        }
    }

    public Result validateSolution(List<List<Integer>> solution){
        return this.checker.validateSolution(solution);
    }
}