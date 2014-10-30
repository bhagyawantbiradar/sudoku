package com.tw.game.result;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Error> errors;

    public Result() {
        this.errors = new ArrayList<Error>();
    }

    public void addError(Error error) {
        this.errors.add(error);
    }

    public boolean isCorrect() {
        return this.errors.size() == 0;
    }

    public List<Error> getErrors() {
        return errors;
    }
}