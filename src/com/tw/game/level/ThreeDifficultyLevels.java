package com.tw.game.level;

import java.util.HashMap;
import java.util.Map;

public class ThreeDifficultyLevels implements SudokuLevels {

    private Map<String, Integer> levels;

    public ThreeDifficultyLevels(String level1, String level2, String level3) {
        this.levels = new HashMap<>();
        levels.put(level1.toLowerCase(),5);
        levels.put(level2.toLowerCase(),4);
        levels.put(level3.toLowerCase(),3);
    }

    public static SudokuLevels getDefaultLevels(){
        return new ThreeDifficultyLevels("easy","medium","difficult");
    }

    @Override
    public Map<String, Integer> getLevels() {
        return levels;
    }
}
