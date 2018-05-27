package com.example.shin.action_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {
    private final Paint paint = new Paint();
    private Bitmap bitmap;
    private  final Callback callback;
    final Rect rect;

    public Droid(Bitmap bitmap, int left, int top, Callback callback) {
        this.rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
        this.callback = callback;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }

    public void move() {
        int distanceFromGround = callback.getDistanceFromGround(this);
        if (distanceFromGround <= 0) {
            return;
        }
        rect.offset(0, 5);
    }

    public interface Callback {
        public int getDistanceFromGround(Droid droid);
    }

}
