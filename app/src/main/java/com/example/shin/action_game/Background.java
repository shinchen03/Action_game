package com.example.shin.action_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Background {
    private Paint paint = new Paint();
    final Rect rect;
    private Bitmap bitmap;

    public Background(Bitmap bitmap, int left, int top, int right, int bottom) {
        rect = new Rect(left, top, right, bottom);
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap (bitmap, canvas.getWidth() - 200, 50, paint);
    }
}
