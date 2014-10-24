package com.tw.game;

import com.tw.game.generator.SolutionGenerator;
import com.tw.game.generator.NumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
    private List<List<Integer>> puzzle = new ArrayList<>();
    private NumberGenerator generator;
    private SolutionGenerator solutionGenerator;

    public Sudoku(NumberGenerator generator, SolutionGenerator solutionGenerator) {
        this.generator = generator;
        solutionGenerator.createSolvedPuzzle();
        this.solutionGenerator = solutionGenerator;
    }
    public List<List<Integer>> getPuzzle() {
        return puzzle;
    }

    public void generatePuzzle() {
        ArrayList<ArrayList<Integer>> solvedPuzzle = solutionGenerator.getSolvedPuzzle();
        for (int i = 0; i < 9; i++) {
            this.puzzle.add(Arrays.asList(new Integer[9]));
            List<Integer> indexes = this.generator.getNumbers();
            for (int j = 0; j < 3; j++)
                this.puzzle.get(i).set(indexes.get(j) - 1, solvedPuzzle.get(i).get(indexes.get(j) - 1));
        }
    }
}