package com.tw.game.generator;

import java.util.ArrayList;

public interface SolutionGenerator {
    public void createSolvedPuzzle();
    public ArrayList<ArrayList<Integer>> getSolvedPuzzle();
}
