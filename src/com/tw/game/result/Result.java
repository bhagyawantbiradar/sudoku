package com.tw.game.result;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Cell> cells;

    public Result() {
        this.cells = new ArrayList<Cell>();
    }

    public void addError(Cell cell) {
        this.cells.add(cell);
    }

    public boolean isCorrect() {
        return this.cells.size() == 0;
    }

    public List<Cell> getCells() {
        return cells;
    }
}