package com.tw.game;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import com.tw.game.generator.RandomNumbersGenerator;
import com.tw.game.generator.SolutionGenerator9X9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuGeneratorActivity extends Activity {

    Sudoku sudoku = new Sudoku(new RandomNumbersGenerator(), new SolutionGenerator9X9(new RandomNumbersGenerator()));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);
        sudoku.generatePuzzle();
        List<List<Integer>> sudokuPuzzle = sudoku.getPuzzle();

        List<List<Integer>> sudokuGrid = new ArrayList<>();
        sudokuGrid.add(Arrays.asList(R.id.r0_c0, R.id.r0_c1, R.id.r0_c2, R.id.r0_c3, R.id.r0_c4, R.id.r0_c5, R.id.r0_c6, R.id.r0_c7, R.id.r0_c8));
        sudokuGrid.add(Arrays.asList(R.id.r1_c0, R.id.r1_c1, R.id.r1_c2, R.id.r1_c3, R.id.r1_c4, R.id.r1_c5, R.id.r1_c6, R.id.r1_c7, R.id.r1_c8));
        sudokuGrid.add(Arrays.asList(R.id.r2_c0, R.id.r2_c1, R.id.r2_c2, R.id.r2_c3, R.id.r2_c4, R.id.r2_c5, R.id.r2_c6, R.id.r2_c7, R.id.r2_c8));
        sudokuGrid.add(Arrays.asList(R.id.r3_c0, R.id.r3_c1, R.id.r3_c2, R.id.r3_c3, R.id.r3_c4, R.id.r3_c5, R.id.r3_c6, R.id.r3_c7, R.id.r3_c8));
        sudokuGrid.add(Arrays.asList(R.id.r4_c0, R.id.r4_c1, R.id.r4_c2, R.id.r4_c3, R.id.r4_c4, R.id.r4_c5, R.id.r4_c6, R.id.r4_c7, R.id.r4_c8));
        sudokuGrid.add(Arrays.asList(R.id.r5_c0, R.id.r5_c1, R.id.r5_c2, R.id.r5_c3, R.id.r5_c4, R.id.r5_c5, R.id.r5_c6, R.id.r5_c7, R.id.r5_c8));
        sudokuGrid.add(Arrays.asList(R.id.r6_c0, R.id.r6_c1, R.id.r6_c2, R.id.r6_c3, R.id.r6_c4, R.id.r6_c5, R.id.r6_c6, R.id.r6_c7, R.id.r6_c8));
        sudokuGrid.add(Arrays.asList(R.id.r7_c0, R.id.r7_c1, R.id.r7_c2, R.id.r7_c3, R.id.r7_c4, R.id.r7_c5, R.id.r7_c6, R.id.r7_c7, R.id.r7_c8));
        sudokuGrid.add(Arrays.asList(R.id.r8_c0, R.id.r8_c1, R.id.r8_c2, R.id.r8_c3, R.id.r8_c4, R.id.r8_c5, R.id.r8_c6, R.id.r8_c7, R.id.r8_c8));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                if (sudokuPuzzle.get(i).get(j) != null) {
                    number.setText(String.valueOf(sudokuPuzzle.get(i).get(j)));
                    number.setTypeface(null, Typeface.BOLD_ITALIC);
                    number.setFocusable(false);
                    number.setTextColor(Color.BLACK);
                }
            }
        }
    }
}
