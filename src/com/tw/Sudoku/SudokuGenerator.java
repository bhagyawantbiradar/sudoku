package com.tw.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SudokuGenerator extends Activity {
    GridLayout gl;
    TextView[] text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);
        gl = (GridLayout) findViewById(R.id.grid_view);
        gl.setColumnCount(9);
        gl.setRowCount(9);
        text = new TextView[81];
        for (int i = 0; i < text.length; i++) {
            text[i] = new EditText(SudokuGenerator.this);
            text[i].setLayoutParams(new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text[i].setTextSize(8);
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(1);
            text[i].setFilters(filterArray);
            text[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            gl.addView(text[i]);
        }
    }
}
