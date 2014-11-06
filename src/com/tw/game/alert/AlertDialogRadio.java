package com.tw.game.alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.ListView;

public class AlertDialogRadio extends DialogFragment {

    private AlertPositiveListener alertPositiveListener;

    public void setAlertPositiveListener(AlertPositiveListener alertPositiveListener) {
        this.alertPositiveListener = alertPositiveListener;
    }

    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ListView listView = ((AlertDialog) dialog).getListView();
            int position = listView.getCheckedItemPosition();
            String level = listView.getItemAtPosition(position).toString();
            alertPositiveListener.onPositiveClick(level);
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] levels = new String[]{"Easy", "Medium", "Difficult"};
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Choose level");
        b.setSingleChoiceItems(levels, position, null);
        b.setPositiveButton("OK", positiveListener);
        b.setNegativeButton("Cancel", null);
        return b.create();
    }
}