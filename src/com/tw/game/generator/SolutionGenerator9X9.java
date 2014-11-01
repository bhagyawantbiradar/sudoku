package com.tw.game.generator;

import java.util.ArrayList;
import java.util.List;

public class SolutionGenerator9X9 implements SolutionGenerator {
    private NumberGenerator generator;

    public SolutionGenerator9X9(NumberGenerator generator) {
        this.generator = generator;
    }

    public List<List<Integer>> createSolvedPuzzle() {
        int size = 3;
        List<List<List<Integer>>> puzzle = getPuzzleWithFirstLineInserted(size);
        int[][] orders = {{1, 2, 0}, {2, 0, 1}};
        insertRowsInBlock(puzzle, size, orders, 0, new int[]{0, 1, 2});
        puzzle.add(3, order2dArrayElements(createCopy(puzzle.get(0)), orders[0]));
        insertRowsInBlock(puzzle, size, orders, 3, orders[0]);
        puzzle.add(6, createCopy(puzzle.get(0)));
        insertRowsInBlock(puzzle, size, orders, 6, orders[1]);
        return formatPuzzle(puzzle);
    }

    private void insertRowsInBlock(List<List<List<Integer>>> puzzle, int size, int[][] orders, int index, int[] order) {
        for (int row = 0; row < size; row++) {
            List<Integer> numbers = puzzle.get(index).get(row);
            for (int i : order) numbers.add(numbers.get(i));
            for (int i = 2; i >= 0; i--) numbers.remove(i);
        }
        for (int[] order1 : orders)
            puzzle.add(order2dArrayElements(createCopy(puzzle.get(index)), order1));
    }

    private List<List<Integer>> order2dArrayElements(List<List<Integer>> numbers, int[] order) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i : order) list.add(numbers.get(i));
        return list;
    }

    private List<List<Integer>> createCopy(List<List<Integer>> numbers) {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> number : numbers) {
            List<Integer> integers = new ArrayList<>();
            for (Integer integer : number) integers.add(integer);
            copy.add(integers);
        }
        return copy;
    }

    private List<List<List<Integer>>> getPuzzleWithFirstLineInserted(int size) {
        List<Integer> numbers = this.generator.getNumbers();
        List<List<List<Integer>>> puzzle = new ArrayList<>();
        puzzle.add(new ArrayList<List<Integer>>());
        for (int row = 0; row < size; row++) {
            puzzle.get(0).add(new ArrayList<Integer>());
            for (int column = 0; column < size; column++)
                puzzle.get(0).get(row).add(column, numbers.get((row * 3) + column));
        }
        return puzzle;
    }

    private List<List<Integer>> formatPuzzle(List<List<List<Integer>>> puzzle) {
        List<List<Integer>> solvedPuzzle = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++) numbers.add(puzzle.get(i).get(j).get(k));
            solvedPuzzle.add(numbers);
        }
        return solvedPuzzle;
    }
}
