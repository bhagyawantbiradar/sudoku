package com.tw.game.checker;

import com.tw.game.result.Result;

import java.util.List;

public interface Checker {
    public Result validateSolution(List<List<Integer>> puzzle);
}
