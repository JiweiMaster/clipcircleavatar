package com.syudy.studyview.BallView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by jiwei on 2018/9/20.
 */

public class BallMoveView extends View {
    private float ballYPosition = 100;
    private float ballXPosition = 100;
    private float ballRadius = 10;
    private int ballColor = Color.BLACK;
    private Paint ballPaint;
    private boolean directionMove = true;


    public BallMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ballPaint = new Paint();
        ballPaint.setColor(ballColor);
        ballPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ballXPosition,ballYPosition,ballRadius,ballPaint);
        int width = getMeasuredWidth();
        if(ballXPosition <= ballRadius){
            directionMove = true;
        }
        if(ballXPosition >= width-ballRadius){
            directionMove = false;
        }
        ballXPosition = directionMove ? ballXPosition+5 : ballXPosition-5;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 100, 500);
    }

}
