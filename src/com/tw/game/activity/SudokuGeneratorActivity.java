package com.tw.game.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tw.game.R;
import com.tw.game.Sudoku;
import com.tw.game.factory.SudokuFactory;
import com.tw.game.helper.SudokuHelper;
import com.tw.game.level.ThreeDifficultyLevels;
import com.tw.game.result.Cell;
import com.tw.game.result.Result;
import com.tw.game.timer.Timer;

import java.util.ArrayList;
import java.util.List;

public class SudokuGeneratorActivity extends Activity {
    private TextView selectedTextView;
    private Sudoku sudoku = new Sudoku(new SudokuFactory(), ThreeDifficultyLevels.getDefaultLevels());
    private List<List<Integer>> sudokuPuzzle = sudoku.getPuzzle();
    private List<List<Integer>> sudokuGrid = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();
    private String level;
    private Timer timer;
    private SudokuHelper sudokuHelper = new SudokuHelper();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);

        TextView timerValue = (TextView) findViewById(R.id.timerValue);
        timer = new Timer(timerValue);
        timer.start(SystemClock.uptimeMillis());

        Intent intent = getIntent();
        level = intent.getStringExtra("level");
        if (level == null) level = getString(R.string.easyLevel);
        sudoku.generatePuzzle(level);
        sudokuHelper.addTextViews(this.sudokuGrid);
        showPuzzle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_save).setTitle(level);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sudokuHelper.confirmQuit(SudokuGeneratorActivity.this);
                return false;
            }
        });
        return true;
    }

    public void solvePuzzle(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to see the solved puzzle?").setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        solveAndShowPuzzle();
                    }
                }).setNegativeButton("No", null);
        builder.create().show();
    }

    private void solveAndShowPuzzle() {
        List<List<Integer>> solvedPuzzle = sudoku.getSolvedPuzzle();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                sudokuHelper.setTextColor(solvedPuzzle, i, j, number);
                sudokuHelper.setProperties(sudokuPuzzle, solvedPuzzle, new Cell(i, j), number, null, false);
            }
        }
    }

    public void loadSolver(View view) {
        finish();
        startActivity(new Intent(this, SudokuSolverActivity.class));
    }

    public void editField(View view) {
        sudokuHelper.editField(view, selectedTextView);
        showResult();
    }

    public void clearNumber(View view) {
        sudokuHelper.clearNumber(selectedTextView);
    }

    private void showPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final EditText number = (EditText) findViewById(sudokuGrid.get(i).get(j));
                sudokuHelper.setProperties(sudokuPuzzle, sudokuPuzzle, new Cell(i, j), number, null, false);
                number.setInputType(InputType.TYPE_NULL);
                number.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        selectedTextView = (TextView) view;
                        sudokuHelper.showKeypad(number, SudokuGeneratorActivity.this);

                        return false;
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

    private void showResult() {
        List<List<Integer>> userSolution = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            userSolution.add(new ArrayList<Integer>());
            for (int j = 0; j < 9; j++) {
                TextView textView = (TextView) findViewById(sudokuGrid.get(i).get(j));
                try {
                    userSolution.get(i).add(Integer.parseInt(String.valueOf(textView.getText())));
                } catch (NumberFormatException e) {
                    return;
                }
            }
        }
        Result result = this.sudoku.validateSolution(userSolution);
        if (result.isCorrect()) {
            String timerValue = timer.getTimerValue();
            timer.stop();
            alertMessageBuilder("Congratulations! You won in " + timerValue + ". Do you want to start a new game?", new Intent(this, SudokuGeneratorActivity.class));
        } else {
            for (Cell cell : cells)
                changeColorTo(cell, Color.parseColor("#2709E6"));
            for (Cell cell : result.getCells())
                changeColorTo(cell, getResources().getColor(R.color.error_background));
            cells = result.getCells();
            Toast.makeText(this, "Your Solution is not right.", Toast.LENGTH_LONG).show();
        }
    }
}