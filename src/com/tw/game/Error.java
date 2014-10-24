package com.tw.game;

public class Error {
    private int row, column;

    public Error(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "Error{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return column == error.column && row == error.row;
    }

    public int getColumn() {
        return column;
    }
}