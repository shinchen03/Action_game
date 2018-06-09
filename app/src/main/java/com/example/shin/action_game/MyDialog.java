package com.example.shin.action_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;


public class MyDialog extends DialogFragment {

    private String highScore;
    private String curScore;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    NoticeDialogListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setMessage("Score: " + curScore + "\n" + "High Score " + highScore)
                .setPositiveButton(R.string.dialog_restart, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(MyDialog.this);
                    }
                })
                .setNegativeButton(R.string.dialog_ranking, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        mListener.onDialogNegativeClick(MyDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        this.setCancelable(false);
        return builder.create();
    }

    public void setScore(String hscore, String cscore) {
        highScore = hscore;
        curScore = cscore;
    }
}