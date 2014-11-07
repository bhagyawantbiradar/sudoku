package com.tw.game.level;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreeDifficultyLevelsTest {

    @Test
    public void testGetLevelsCreatesAMapOfSize3AndContainsAllTheLevelsProvidedToIt() throws Exception {

        String[] levels = {"e", "m", "f"};
        Map<String, Integer> map = new ThreeDifficultyLevels(levels[0], levels[1], levels[2]).getLevels();
        assertEquals(3, map.size());
        for (String level : levels) assertTrue(map.containsKey(level));
    }
}