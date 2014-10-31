package com.tw.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tw.game.alert.AlertDialogRadio;
import com.tw.game.alert.AlertPositiveListener;
import com.tw.game.factory.SudokuFactory;
import com.tw.game.level.ThreeDifficultyLevels;
import com.tw.game.result.Cell;
import com.tw.game.result.Result;

import java.util.ArrayList;
import java.util.List;

public class SudokuGeneratorActivity extends Activity implements AlertPositiveListener {
    private TextView selectedTextView;
    private Sudoku sudoku = new Sudoku(new SudokuFactory(), ThreeDifficultyLevels.getDefaultLevels());
    private List<List<Integer>> sudokuPuzzle = sudoku.getPuzzle();
    private List<List<Integer>> sudokuGrid = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);
        Intent intent = getIntent();
        String level = intent.getStringExtra("level");
        if (level == null)  level = getString(R.string.easyLevel);
        sudoku.generatePuzzle(level);
        SudokuActivity.addTextViews(this.sudokuGrid);
        showPuzzle();
    }

    public void onLevelSelection(View v) {
        FragmentManager manager = getFragmentManager();
        AlertDialogRadio alert = new AlertDialogRadio();
        alert.setAlertPositiveListener(SudokuGeneratorActivity.this);
        Bundle b = new Bundle();
        b.putInt("position", 0);
        alert.setArguments(b);
        alert.show(manager, "alert_dialog_radio");
    }

    public void solvePuzzle(View view) {
        ArrayList<ArrayList<Integer>> solvedPuzzle = sudoku.getSolvedPuzzle();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                number.setText(String.valueOf(solvedPuzzle.get(i).get(j)));
                number.setTextColor(Color.parseColor("#2709E6"));
                if (sudokuPuzzle.get(i).get(j) != null) {
                    number.setText(String.valueOf(solvedPuzzle.get(i).get(j)));
                    SudokuActivity.setProperties(number);
                }
            }
        }
    }

    public void showResult(View view) {
        List<List<Integer>> userSolution = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            userSolution.add(new ArrayList<Integer>());
            for (int j = 0; j < 9; j++) {
                TextView textView = (TextView) findViewById(sudokuGrid.get(i).get(j));
                try {
                    userSolution.get(i).add(Integer.parseInt(String.valueOf(textView.getText())));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Some Blocks are empty yet.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        Result result = this.sudoku.validateSolution(userSolution);
        if (result.isCorrect())
            alertMessageBuilder("Congratulations! You won. Do you want to start a new game?", new Intent(this, SudokuGeneratorActivity.class));
        else {
            for (Cell cell : cells)
                changeColorTo(cell, Color.parseColor("#2709E6"));
            for (Cell cell : result.getCells())
                changeColorTo(cell, getResources().getColor(R.color.error_background));
            cells = result.getCells();
            Toast.makeText(this, "Your Solution is not right.", Toast.LENGTH_LONG).show();
        }
    }

    public void loadSolver(View view) {
        finish();
        startActivity(new Intent(this, SudokuSolverActivity.class));
    }

    public void editField(View view) {
        if (selectedTextView == null) return;
        selectedTextView.setText(((Button) view).getText());
    }

    private void showPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                if (sudokuPuzzle.get(i).get(j) != null) {
                    number.setText(String.valueOf(sudokuPuzzle.get(i).get(j)));
                    SudokuActivity.setProperties(number);
                }
                number.setInputType(InputType.TYPE_NULL);
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
    }


    private void changeColorTo(Cell cell, int color) {
        if (sudokuPuzzle.get(cell.getRow()).get(cell.getColumn()) == null)
            ((EditText) findViewById(sudokuGrid.get(cell.getRow()).get(cell.getColumn()))).setTextColor(color);
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

    @Override
    public void onPositiveClick(String level) {
        Intent intent = new Intent(SudokuGeneratorActivity.this, SudokuGeneratorActivity.class);
        intent.putExtra("level", level);
        finish();
        startActivity(intent);
    }
}