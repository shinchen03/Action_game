package com.example.shin.action_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// import android.view.View;

public class GameView extends SurfaceView implements Droid.Callback, SurfaceHolder.Callback {

    private static final long FPS = 60;
    private class DrawThread extends Thread {
        boolean isFinished;
        @Override
        public void run() {
            SurfaceHolder holder = getHolder();
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawGame(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(1000 / FPS);
                } catch (InterruptedException e) {

                }
            }
        }
    }
    private DrawThread drawThread;
    public void startDrawThread() {
        stopDrawThread();
        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }
        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    @Override
    public void surfaceCreated (SurfaceHolder holder) {
        startDrawThread();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }

    private static final int START_GROUND_HEIGHT = 50;
    private static final int GROUND_MOVE_TO_LEFT = 10;
    private static final int ADD_GROUND_COUNT = 5;
    private static final int GROUND_WIDTH = 340;
    private static final int GROUND_BLOCK_HEIGHT = 100;
    private final List<Ground> groundList = new ArrayList<>();
    private final Random rand = new Random();
    private Ground lastGround;
    private Droid droid;
    private static final int MAX_TOUCH_TIME = 500;
    private long touchDownStartTime;
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownStartTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_UP:
                jumpDroid(10);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void jumpDroid(float power) {
        float time = System.currentTimeMillis() - touchDownStartTime;
        touchDownStartTime = 0;

        if (getDistanceFromGround(droid) != 0) {
            return;
        }

        if (time > MAX_TOUCH_TIME) {
            time = MAX_TOUCH_TIME;
        }

        droid.jump(time / MAX_TOUCH_TIME);
    }

    public interface Callback {
        public void onGameOver();
    }
    private Callback callback;
    public void setCallback (Callback callback) {
        this.callback = callback;
    }
    private final Handler handler;
    private boolean isGameOver;

    public GameView(Context context) {
        super(context);
        handler = new Handler();
        getHolder().addCallback(this);
    }

    public void drawGame(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawColor(Color.WHITE);

//        if (droidBitmap == null) {
//            droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanghost);
//        }
        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanred);
            bitmap= Bitmap.createScaledBitmap(bitmap, 150, 150, false);
            droid = new Droid(bitmap, 0, 0, this);
        }
        lastGround = new Ground(0, 3*height/4, width, height);
        groundList.add(lastGround);
        if (lastGround.isShown(width, height)) {
            for (int i=0; i<ADD_GROUND_COUNT; i++) {
                int left = lastGround.rect.right;
                int groundHeight = rand.nextInt(height / GROUND_BLOCK_HEIGHT)
                        * GROUND_BLOCK_HEIGHT / 2 + START_GROUND_HEIGHT;
                lastGround = new Ground(left, height - groundHeight,
                        left + GROUND_WIDTH, height);
                groundList.add(lastGround);
            }
        }

        for (int i=0; i<groundList.size(); i++) {
            Ground ground = groundList.get(i);
            if (ground.isAvailable()) {
                ground.move(GROUND_MOVE_TO_LEFT);

                if (ground.isShown(width, height)) {
                    ground.draw(canvas);
                }
            } else {
                groundList.remove(ground);
                i--;
            }
        }
//        if (ground == null) {
//            ground = new Ground(0, height = START_GROUND_HEIGHT, width, height);
//        }

        droid.move(); // move down

        droid.draw(canvas);
        // invalidate(); // loop this method
    }

    @Override
    public int getDistanceFromGround(Droid droid) {
//        boolean horizontal = !(droid.rect.left >= ground.rect.right
//                || droid.rect.right <= ground.rect.left);
        int width = getWidth();
        int height = getHeight();
        for (int i=0; i<groundList.size(); i++) {
            Ground ground = groundList.get(i);
            if (!ground.isShown(width, height)) {
                continue;
            }
            boolean horizontal = !(droid.rect.left >= ground.rect.right
                    || droid.rect.right <= ground.rect.left);
            if (horizontal) {
                int distanceFromGround = ground.rect.top - droid.rect.bottom;
                if (distanceFromGround < 0) {
                    gameOver();
                    return Integer.MAX_VALUE;
                }
                return distanceFromGround;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void gameOver() {
        if (isGameOver) return;
        isGameOver = true;
        droid.shutdown();
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onGameOver();
            }
        });
    }
}
