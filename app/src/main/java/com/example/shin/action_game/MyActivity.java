package com.example.shin.action_game;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity implements GameView.Callback, MyDialog.NoticeDialogListener {

    private GameView gameView;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        gameView = new GameView(this);
        gameView.setCallback(this);
        myDb = new DataBaseHelper(this);
        Button startBtn = (Button) findViewById(R.id.startGame);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_my);
                setContentView(gameView);
            }
        });
    }

    @Override
    public void onGameOver() {
        Toast.makeText(this, "Game OVer", Toast.LENGTH_LONG).show();
        showNoticeDialog();
        String score = String.valueOf(GameView.score);
        if (myDb.updateData("0", score)) {
            Toast.makeText(this, "New High Score!" + score, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.startDrawThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopDrawThread();
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new MyDialog();
        dialog.show(getFragmentManager(), "test");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        gameView = new GameView(this);
        gameView.setCallback(this);
        setContentView(R.layout.activity_my);
        setContentView(gameView);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
}
