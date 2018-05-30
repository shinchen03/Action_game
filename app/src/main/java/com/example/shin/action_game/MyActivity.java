package com.example.shin.action_game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MyActivity extends Activity implements GameView.Callback {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        gameView = new GameView(this);
        gameView.setCallback(this);
        setContentView(R.layout.activity_my);
        setContentView(gameView);
    }

    @Override
    public void onGameOver() {
        Toast.makeText(this, "Game OVer", Toast.LENGTH_LONG).show();
    }
}
