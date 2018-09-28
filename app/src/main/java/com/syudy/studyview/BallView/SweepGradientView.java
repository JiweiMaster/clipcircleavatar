package com.syudy.studyview.BallView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiwei on 2018/9/25.
 */

public class SweepGradientView extends View {
    private Paint mPaint;
    private Paint whitePaint;
    private float mRotate;
    private Matrix mMatrix = new Matrix();
    private Shader mShader;
    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setFocusable(true);
        setFocusableInTouchMode(true);
        float x = 160;
        float y = 100;
        mShader = new SweepGradient(x,y,new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.GREEN},null);
        mPaint.setShader(mShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = 160;
        float y = 100;
        canvas.translate(400,400);//将当前画布的圆点平移300,300，默认的
        canvas.drawColor(Color.WHITE);
        mMatrix.setRotate(mRotate,x,y);//矩阵旋转导致sahder旋转
        mShader.setLocalMatrix(mMatrix);
        mRotate +=3;
        if(mRotate >= 360){
            mRotate = 0;
        }
        invalidate();
        canvas.drawCircle(x,y,300,mPaint);
        canvas.drawCircle(x,y,100,whitePaint);
    }
}
