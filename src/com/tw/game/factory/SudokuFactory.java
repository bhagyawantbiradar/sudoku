package com.tw.game.factory;

import com.tw.game.checker.Checker;
import com.tw.game.checker.SolutionChecker;
import com.tw.game.generator.NumberGenerator;
import com.tw.game.generator.RandomNumbersGenerator;
import com.tw.game.generator.SolutionGenerator;
import com.tw.game.generator.SolutionGenerator9X9;

public class SudokuFactory implements Factory {
    @Override
    public SolutionGenerator getSolutionGenerator() {
        return new SolutionGenerator9X9(new RandomNumbersGenerator());
    }

    @Override
    public Checker getSolutionChecker() {
        return new SolutionChecker();
    }

    @Override
    public NumberGenerator getRandomNumberGenerator() {
        return new RandomNumbersGenerator();
    }
}
