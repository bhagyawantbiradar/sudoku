package com.tw.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tw.game.checker.SolutionChecker;
import com.tw.game.solver.SudokuSolver;

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
        setEditTextProperties();
    }

    public void showResult(View view) {
        for (int i = 0; i < 9; i++) {
            puzzle.add(Arrays.asList(new Integer[9]));
            for (int j = 0; j < 9; j++) {
                EditText editText = (EditText) findViewById(sudokuGrid.get(i).get(j));
                if (editText.getText().toString().equals(""))
                    puzzle.get(i).set(j, 0);
                else
                    puzzle.get(i).set(j, Integer.parseInt(editText.getText().toString()));
            }
        }
        SudokuSolver sudokuSolver = new SudokuSolver(new SolutionChecker());
        sudokuSolver.solvePuzzle(puzzle);
        showSolvePuzzle(puzzle);
    }

    public void clearPuzzle(View view) {
        setEditTextProperties();
    }

    private void setEditTextProperties() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                number.setText("");
                number.setInputType(InputType.TYPE_NULL);
                number.setKeyListener(null);
                number.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        selectedTextView = (TextView) view;
                        return false;
                    }
                });
                number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus && v.getBackground().equals(Color.WHITE)) {
                            v.setBackgroundColor(Color.parseColor("#d3d3d3"));
                        }
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

    public void clearNumber(View view) {
        if (selectedTextView == null) return;
        selectedTextView.setText("");
    }

    private void showSolvePuzzle(List<List<Integer>> solvedPuzzle) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                number.setText(String.valueOf(solvedPuzzle.get(i).get(j)));
                number.setTextColor(Color.parseColor("#2709E6"));
                if (puzzle.get(i).get(j) != null) {
                    number.setText(String.valueOf(solvedPuzzle.get(i).get(j)));
                    SudokuActivity.setProperties(number);
                }
            }
        }
    }
}