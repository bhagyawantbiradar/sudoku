package com.tw.game.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.tw.game.R;
import com.tw.game.alert.AlertDialogRadio;
import com.tw.game.alert.AlertPositiveListener;

public class HomeActivity extends Activity implements AlertPositiveListener, AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.homeview);
    }

    public void loadSolver(View view) {
        Intent intent = new Intent(this, SudokuSolverActivity.class);
        finish();
        startActivity(intent);
    }

    public void onLevelSelection(View v) {
        FragmentManager manager = getFragmentManager();
        AlertDialogRadio alert = new AlertDialogRadio();
        alert.setAlertPositiveListener(this);
        Bundle b = new Bundle();
        b.putInt("position", 0);
        alert.setArguments(b);
        alert.show(manager, "alert_dialog_radio");
    }

    @Override
    public void onPositiveClick(String level) {
        Intent intent = new Intent(this, SudokuGeneratorActivity.class);
        intent.putExtra("level", level);
        finish();
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selState = (String) adapterView.getSelectedItem();
        if (!selState.equals("Select Level")) {
            Intent intent = new Intent(this, SudokuGeneratorActivity.class);
            intent.putExtra("level", selState);
            intent.putExtra("selection", i);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}