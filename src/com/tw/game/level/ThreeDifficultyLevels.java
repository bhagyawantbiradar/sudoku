package com.tw.game.level;

import java.util.HashMap;
import java.util.Map;

public class ThreeDifficultyLevels implements SudokuLevels {

    private Map<String, Integer> levels;

    public ThreeDifficultyLevels(String level1, String level2, String level3) {
        this.levels = new HashMap<>();
        levels.put(level1,3);
        levels.put(level2,4);
        levels.put(level3,5);
    }

    @Override
    public Map<String, Integer> getLevels() {
        return levels;
    }
}
