package com.syudy.studyview.BallView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by jiwei on 2018/9/21.
 */

public class CheckCodingView extends View {

    private float Width = 160;
    private float  Height = 50;
    private Paint wordPaint;
    private Paint linePaint;
    char checkChar[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
            ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public CheckCodingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        wordPaint = new Paint();
        wordPaint.setColor(Color.BLACK);
        wordPaint.setAntiAlias(true);
        wordPaint.setTextSize(40f);
        wordPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(1,1,160,60,linePaint);
        canvas.drawText(checkChar[getRandom()]+"",10,50,wordPaint);
        canvas.drawText(checkChar[getRandom()]+"",50,50,wordPaint);
        canvas.drawText(checkChar[getRandom()]+"",90,50,wordPaint);
        canvas.drawText(checkChar[getRandom()]+"",130,50,wordPaint);

        linePaint.setColor(Color.BLUE);
        canvas.drawCircle(300,100,50,linePaint);
        for(int i=0; i<12; i++){
            canvas.drawLine(250,100,270,100,linePaint);
            canvas.rotate(30,300,100);
        }
        canvas.save();
        canvas.restore();
    }

    private static int getRandom(){
        int min = 0;
        int max = 52;
        Random random = new Random();
        return random.nextInt((max)%(max-min+1))+min;
    }
}
