package com.example.shin.action_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {

    private static final int COLLISION_MARGIN_LEFT = 3;
    private static final int COLLISION_MARGIN_RIGHT = 3;
    private static final float GRAVITY = 0.8f;
    private static final float WEIGHT = GRAVITY  * 60;
    private float acceleration = 0;
    private final Paint paint = new Paint();
    private Bitmap bitmap;
    private  final Callback callback;
    final Rect rect;

    public void jump(float power) {
        acceleration = power * WEIGHT;
    }

    public Droid(Bitmap bitmap, int left, int top, Callback callback) {
        //this.rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        int rectLeft = left + COLLISION_MARGIN_LEFT;
        int rectRight = left + bitmap.getWidth() - COLLISION_MARGIN_RIGHT;
        this.rect = new Rect(rectLeft, top, rectRight, top + bitmap.getHeight());
        this.bitmap = bitmap;
        this.callback = callback;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }

    public void move() {
        acceleration -= GRAVITY;
        int distanceFromGround = callback.getDistanceFromGround(this);
//        if (distanceFromGround <= 0) {
//            return;
//        }
        if (acceleration < 0 && acceleration < -distanceFromGround) {
            acceleration = -distanceFromGround;
        }
        rect.offset(0, -Math.round(acceleration));
    }

    public interface Callback {
        public int getDistanceFromGround(Droid droid);
    }

    public void shutdown() {
        acceleration = 0;
    }

    public void clear() {

    }
}
