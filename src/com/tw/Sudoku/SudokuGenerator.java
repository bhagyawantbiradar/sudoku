package com.tw.sudoku;

import android.app.Activity;
import android.os.Bundle;

public class SudokuGenerator extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sudoku);
    }
}
