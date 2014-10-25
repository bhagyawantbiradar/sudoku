package com.tw.game;

import com.tw.game.generator.NumberGenerator;
import com.tw.game.generator.SolutionGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
    private List<List<Integer>> puzzle = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> solvedPuzzle;
    private NumberGenerator generator;

    public Sudoku(NumberGenerator generator, SolutionGenerator solutionGenerator) {
        this.generator = generator;
        solutionGenerator.createSolvedPuzzle();
        this.solvedPuzzle = solutionGenerator.getSolvedPuzzle();
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
}