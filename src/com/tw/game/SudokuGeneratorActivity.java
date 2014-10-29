package com.tw.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import com.tw.game.factory.SudokuFactory;
import com.tw.game.level.ThreeDifficultyLevels;
import com.tw.game.result.Error;
import com.tw.game.result.Result;

import java.util.*;

public class SudokuGeneratorActivity extends Activity {
    private String easy = "Easy", medium = "Medium", difficult = "Difficult";
    private TextView selectedTextView;
    private String level;
    private Sudoku sudoku = new Sudoku(new SudokuFactory(),ThreeDifficultyLevels.getDefaultLevels());
    private List<List<Integer>> sudokuPuzzle = sudoku.getPuzzle();
    private List<List<Integer>> sudokuGrid = new ArrayList<>();
    private List<Error> errors = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);
        Intent intent = getIntent();
        this.level = intent.getStringExtra("level");
        Map<String, Integer> buttonAndIDs = getDifficultyLevelMap();
        if (this.level == null) {
            this.level = easy;
            ((RadioButton) findViewById(R.id.easy)).setChecked(true);
        }
        else
            for(int i=0; i < buttonAndIDs.size(); i++)
                ((RadioButton) findViewById(buttonAndIDs.get(level))).setChecked(true);
        sudoku.generatePuzzle(this.level);
        SudokuActivity.addTextViews(this.sudokuGrid);
        showPuzzle();
    }

    public Map<String, Integer> getDifficultyLevelMap() {
        Map<String, Integer> buttonAndIDs = new HashMap<>(3);
        buttonAndIDs.put(easy, R.id.easy);
        buttonAndIDs.put(medium, R.id.medium);
        buttonAndIDs.put(difficult, R.id.difficult);
        return buttonAndIDs;
    }

    private void showPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                if (sudokuPuzzle.get(i).get(j) != null) {
                    number.setText(String.valueOf(sudokuPuzzle.get(i).get(j)));
                    SudokuActivity.setProperties(number);
                }
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
    }

    public void editField(View view) {
        if (selectedTextView == null) return;
        selectedTextView.setText(((Button) view).getText());
    }

    public void solvePuzzle(View view) {
        ArrayList<ArrayList<Integer>> solvedPuzzle = sudoku.getSolvedPuzzle();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                SudokuActivity.showNumbers(solvedPuzzle, i, j, number, sudokuPuzzle);
            }
        }
    }

    public void showResult(View view) {
        List<List<Integer>> userSolution = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            userSolution.add(new ArrayList<Integer>());
            for (int j = 0; j < 9; j++) {
                TextView textView = (TextView) findViewById(sudokuGrid.get(i).get(j));
                userSolution.get(i).add(Integer.parseInt(String.valueOf(textView.getText())));
            }
        }
        Result result = this.sudoku.validateSolution(userSolution);
        if (result.isCorrect()) {
            Intent yesAction = new Intent(SudokuGeneratorActivity.this, SudokuGeneratorActivity.class);
            alertMessageBuilder("Congratulations! You won. Do you want to start a new game?", yesAction);
        } else {
            for (Error error : errors)
                changeColorTo(error, Color.parseColor("#2709E6"));
            for (Error error : result.getErrors())
                    changeColorTo(error, getResources().getColor(R.color.error_background));
            errors = result.getErrors();
            Toast.makeText(this, "Your Solution is not right.", Toast.LENGTH_LONG).show();
        }
    }

    private void changeColorTo(Error error, int color) {
        if (sudokuPuzzle.get(error.getRow()).get(error.getColumn()) == null)
            ((EditText) findViewById(sudokuGrid.get(error.getRow()).get(error.getColumn()))).setTextColor(color);
    }

    public void onLevelChange(View view) {
        Intent intent = new Intent(SudokuGeneratorActivity.this, SudokuGeneratorActivity.class);
        intent.putExtra("level", ((RadioButton) view).getText().toString());
        finish();
        startActivity(intent);
        this.sudokuPuzzle = sudoku.getPuzzle();
        showPuzzle();
    }

    private void alertMessageBuilder(String message, final Intent yesAction) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                        startActivity(yesAction);
                    }
                }).setNegativeButton("No", null);
        builder.create().show();
    }

    public void loadSolver(View view){
        Intent intent = new Intent(this,SudokuSolverActivity.class);
        finish();
        startActivity(intent);
    }
}