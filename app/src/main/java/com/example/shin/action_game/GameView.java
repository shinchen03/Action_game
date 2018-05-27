package com.example.shin.action_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View {
    private static final int START_GROUND_HEIGHT = 50;
    private Ground ground;
    private Droid droid;

    public GameView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();


//        if (droidBitmap == null) {
//            droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanghost);
//        }
        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droid = new Droid(bitmap, 0, 0);
        }

        if (ground == null) {
            ground = new Ground(0, height = START_GROUND_HEIGHT, width, height);
        }

        droid.move(); // move down

        droid.draw(canvas);
        ground.draw(canvas);
        invalidate(); // loop this method
    }
}
