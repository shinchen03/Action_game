package com.example.shin.action_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyActivity extends AppCompatActivity {

    private GameView gameView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        gameView = new GameView(this);
        setContentView(R.layout.activity_my);
        setContentView(gameView);
    }
}
