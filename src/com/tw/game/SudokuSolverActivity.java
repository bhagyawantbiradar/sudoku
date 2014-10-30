package com.tw.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tw.game.factory.SudokuFactory;
import com.tw.game.level.ThreeDifficultyLevels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolverActivity extends Activity {
    private TextView selectedTextView;
    private List<List<Integer>> sudokuGrid = new ArrayList<>();
    private List<List<Integer>> solvedPuzzle = new ArrayList<>();
    private List<List<Integer>> puzzle = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.solver);
        SudokuActivity.addTextViews(this.sudokuGrid);
    }

    public void showResult(View view) {
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

    public void clearPuzzle(View view) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                number.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(number.getWindowToken(), 0);
                number.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        selectedTextView = (TextView) view;
                        return false;
                    }
                });
            }

    }

    public void loadPuzzle(View view) {
        finish();
        startActivity(new Intent(this, SudokuGeneratorActivity.class));
    }

    public void editField(View view) {
        if (selectedTextView == null) return;
        selectedTextView.setText(((Button) view).getText());
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