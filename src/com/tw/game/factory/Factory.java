package com.tw.game.factory;

import com.tw.game.checker.Checker;
import com.tw.game.generator.NumberGenerator;
import com.tw.game.generator.SolutionGenerator;

public interface Factory {
    public SolutionGenerator getSolutionGenerator();

    public Checker getSolutionChecker();

    public NumberGenerator getRandomNumberGenerator();
}
