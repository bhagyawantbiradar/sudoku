package com.tw.game.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tw.game.activity.HomeActivity;
import com.tw.game.R;
import com.tw.game.result.Cell;

import java.util.Arrays;
import java.util.List;

public class SudokuHelper {

    private PopupWindow popupWindow;

    public void addTextViews(List<List<Integer>> sudokuGrid) {
        sudokuGrid.add(Arrays.asList(R.id.r0_c0, R.id.r0_c1, R.id.r0_c2, R.id.r0_c3, R.id.r0_c4, R.id.r0_c5, R.id.r0_c6, R.id.r0_c7, R.id.r0_c8));
        sudokuGrid.add(Arrays.asList(R.id.r1_c0, R.id.r1_c1, R.id.r1_c2, R.id.r1_c3, R.id.r1_c4, R.id.r1_c5, R.id.r1_c6, R.id.r1_c7, R.id.r1_c8));
        sudokuGrid.add(Arrays.asList(R.id.r2_c0, R.id.r2_c1, R.id.r2_c2, R.id.r2_c3, R.id.r2_c4, R.id.r2_c5, R.id.r2_c6, R.id.r2_c7, R.id.r2_c8));
        sudokuGrid.add(Arrays.asList(R.id.r3_c0, R.id.r3_c1, R.id.r3_c2, R.id.r3_c3, R.id.r3_c4, R.id.r3_c5, R.id.r3_c6, R.id.r3_c7, R.id.r3_c8));
        sudokuGrid.add(Arrays.asList(R.id.r4_c0, R.id.r4_c1, R.id.r4_c2, R.id.r4_c3, R.id.r4_c4, R.id.r4_c5, R.id.r4_c6, R.id.r4_c7, R.id.r4_c8));
        sudokuGrid.add(Arrays.asList(R.id.r5_c0, R.id.r5_c1, R.id.r5_c2, R.id.r5_c3, R.id.r5_c4, R.id.r5_c5, R.id.r5_c6, R.id.r5_c7, R.id.r5_c8));
        sudokuGrid.add(Arrays.asList(R.id.r6_c0, R.id.r6_c1, R.id.r6_c2, R.id.r6_c3, R.id.r6_c4, R.id.r6_c5, R.id.r6_c6, R.id.r6_c7, R.id.r6_c8));
        sudokuGrid.add(Arrays.asList(R.id.r7_c0, R.id.r7_c1, R.id.r7_c2, R.id.r7_c3, R.id.r7_c4, R.id.r7_c5, R.id.r7_c6, R.id.r7_c7, R.id.r7_c8));
        sudokuGrid.add(Arrays.asList(R.id.r8_c0, R.id.r8_c1, R.id.r8_c2, R.id.r8_c3, R.id.r8_c4, R.id.r8_c5, R.id.r8_c6, R.id.r8_c7, R.id.r8_c8));
    }

    public void setProperties(List<List<Integer>> sudokuPuzzle, List<List<Integer>> solvedPuzzle, Cell cell, EditText number, Integer empty, boolean focusable) {
        if (sudokuPuzzle.get(cell.getRow()).get(cell.getColumn()) != empty) {
            number.setText(String.valueOf(solvedPuzzle.get(cell.getRow()).get(cell.getColumn())));
            number.setTypeface(null, Typeface.BOLD_ITALIC);
            number.setFocusable(focusable);
            number.setEnabled(focusable);
            number.setTextColor(Color.BLACK);
        }
    }

    public void editField(View view, TextView selectedTextView) {
        if (selectedTextView != null) selectedTextView.setText(((Button) view).getText());
        hideKeypad();
    }

    public void clearNumber(TextView selectedTextView) {
        if (selectedTextView != null) selectedTextView.setText("");
        hideKeypad();
    }

    public void setTextColor(List<List<Integer>> solvedPuzzle, int i, int j, EditText number) {
        number.setText(String.valueOf(solvedPuzzle.get(i).get(j)));
        number.setTextColor(Color.parseColor("#2709E6"));
    }

    public void confirmQuit(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want to quit this puzzle?").setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        activity.finish();
                        activity.startActivity(new Intent(activity, HomeActivity.class));
                    }
                }).setNegativeButton("No", null);
        builder.create().show();
    }

    public void showKeypad(EditText number, Context context) {
        if (popupWindow != null) return;
        View popupView = LayoutInflater.from(context).inflate(R.layout.keypad, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ShapeDrawable());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow = null;
                return false;
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(number);
    }

    public void hideKeypad() {
        popupWindow.dismiss();
        popupWindow = null;
    }
}