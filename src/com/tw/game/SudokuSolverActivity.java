package com.tw.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.tw.game.factory.SudokuFactory;
import com.tw.game.level.ThreeDifficultyLevels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolverActivity extends Activity {
    private List<List<Integer>> sudokuGrid = new ArrayList<>();
    private List<List<Integer>> solvedPuzzle = new ArrayList<>();
    private List<List<Integer>> puzzle = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.solver);
        SudokuActivity.addTextViews(this.sudokuGrid);
    }

    public void solveUserPuzzle(View view) {
        for (int i = 0; i < 9; i++) {
            puzzle.add(Arrays.asList(new Integer[9]));
            for (int j = 0; j < 9; j++) {
                EditText editText = (EditText) findViewById(sudokuGrid.get(i).get(j));
                if (editText.getText().toString().equals(""))
                    puzzle.get(i).set(j, null);
                else
                    puzzle.get(i).set(j, Integer.parseInt(editText.getText().toString()));
            }
        }
        showSolvePuzzle(new Sudoku(new SudokuFactory(), ThreeDifficultyLevels.getDefaultLevels()).getSolvedPuzzle());
    }

    private void showSolvePuzzle(ArrayList<ArrayList<Integer>> solvedPuzzle) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                SudokuActivity.showNumbers(solvedPuzzle, i, j, number, puzzle);
            }
        }
    }
}