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

public class MyActivity extends AppCompatActivity implements GameView.Callback {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        gameView = new GameView(this);
        gameView.setCallback(this);
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
        DialogFragment dialog = new MyDialog();

        dialog.show(getFragmentManager(), "test");
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

    public void reStart() {
        gameView = new GameView(this);
        gameView.setCallback(this);
        setContentView(R.layout.activity_my);
        setContentView(gameView);
    }
}
