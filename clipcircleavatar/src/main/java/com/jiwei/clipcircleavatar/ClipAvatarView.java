package com.jiwei.clipcircleavatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiwei on 2018/9/25.
 */

public class ClipAvatarView extends View {
    private static final int NONE = 0;
    private static final int MOVE = 1;
    private static final int ZOOM = 2;
//  图片的模式
    private int MODE = NONE;
    private static String TAG = "ClipAvatarView";
    private Paint mCanvasPaint;

    private Bitmap dstBitmap;
    private Bitmap backBitmap;

    int canvasWidth;
    int canvasHeight;

    private float preX =0, preY = 0, currentX = 0,currentY = 0, positionX = 0, positionY = 0;

    private float pointPressDownLength = 0, pointMovelength = 0, scaleValue = 1.0f;

    private Matrix mMatrix = new Matrix();//一个单位矩阵

    private float MinScale = 0.1f;
    private float MaxScale = 10.0f;

    public ClipAvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCanvasPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        try {
            InputStream inputStream = context.getApplicationContext().getAssets().open("example.png");
            dstBitmap = BitmapFactory.decodeStream(inputStream);//获取到图片
        } catch (IOException e) {
            Log.e(TAG,"no sourceBitmap");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasHeight = getHeight();
        canvasWidth = getWidth();
//      现在画的是一个圆形的轮廓
        backBitmap = Bitmap.createBitmap(canvasWidth,canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas backCanvas = new Canvas(backBitmap);
        backCanvas.drawARGB(150,0,0,0);//画布设置半透明颜色
        Paint backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(Color.WHITE);
        backPaint.setStyle(Paint.Style.STROKE);
        backPaint.setStrokeWidth(5);
        backCanvas.drawCircle(canvasWidth/2.0f,canvasHeight/2.0f,300.0f,backPaint);
//      里面的圆形
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap circleBitmap = Bitmap.createBitmap(canvasWidth,canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas circleCanvas = new Canvas(circleBitmap);
        circleCanvas.drawCircle(canvasWidth/2.0f,canvasHeight/2.0f,302.0f,circlePaint);
//      扣掉里面的圆形
        Paint newPaint = new Paint();
        newPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        backCanvas.drawBitmap(circleBitmap,0,0,newPaint);
        newPaint.setXfermode(null);
        //初始化图片的位置
        positionX = (canvasWidth-dstBitmap.getWidth())/2;
        positionY = (canvasHeight-dstBitmap.getHeight())/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制头像图片
        mCanvasPaint.reset();
//      设置图片的放大矩阵
        mMatrix.setScale(scaleValue,scaleValue);
        mMatrix.setRotate(45.0f);
        mMatrix.setTranslate(positionX,positionY);
//      放大矩阵通过postScale矩阵来实现
        mMatrix.postScale(scaleValue,scaleValue,positionX,positionY);
        canvas.drawBitmap(dstBitmap,mMatrix,mCanvasPaint);
        mCanvasPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//取下层非交集部分
        canvas.drawBitmap(backBitmap,0, 0,mCanvasPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                OnActionDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                OnActionPointerDown(event);//多点触摸的Action
                break;
            case MotionEvent.ACTION_MOVE:
                OnActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                OnActionUp(event);
                break;
        }
        return true;
    }

    private void OnActionDown(MotionEvent event){
        if(event.getPointerCount() == 1){
            MODE = MOVE;
            preX = event.getX();
            preY = event.getY();
        }
    }

    private void OnActionPointerDown(MotionEvent event){
        if(event.getPointerCount() == 2){
            MODE = ZOOM;
            float pointX0 = event.getX(0);
            float pointX1 = event.getX(1);
            float pointY0 = event.getY(0);
            float pointY1 = event.getY(1);
            pointPressDownLength  = (float) Math.sqrt((pointX1-pointX0)*(pointX1-pointX0) + (pointY1-pointY0)*(pointY1-pointY0));
        }
    }

    private void OnActionMove(MotionEvent event){
        if(MODE == MOVE){
            currentX = event.getX();
            currentY = event.getY();
            float dx = currentX-preX;
            float dy = currentY-preY;
            positionX += dx;
            positionY += dy;
            preX = currentX;
            preY = currentY;
            invalidate();
        }
        if(MODE == ZOOM){
            if(event.getPointerCount() == 2){
                float pointX0 = event.getX(0);
                float pointX1 = event.getX(1);
                float pointY0 = event.getY(0);
                float pointY1 = event.getY(1);

                positionX = (pointX0+pointX1)/2-(dstBitmap.getWidth())*scaleValue/2;
                positionY = (pointY0+pointY1)/2-(dstBitmap.getHeight())*scaleValue/2;

                pointMovelength  = (float) Math.sqrt((pointX1-pointX0)*(pointX1-pointX0) + (pointY1-pointY0)*(pointY1-pointY0));
                float incrementLength = pointMovelength - pointPressDownLength;//两个手指之间的增量
                if(Math.abs(incrementLength) > 10 && pointPressDownLength != 0){
                    Log.d(TAG,"plus=>"+incrementLength/4000);
                    scaleValue += (incrementLength/8000);
                    if(scaleValue < MinScale){
                        scaleValue = MinScale;
                    }
                    if(scaleValue > MaxScale){
                        scaleValue = MaxScale;
                    }
                    invalidate();
                }
            }
        }
    }
    private void OnActionUp(MotionEvent event){
        preX = event.getX();
        preY = event.getY();
        MODE = NONE;
    }

    public void setSourceBitmap(Bitmap sourceBitmap){
        dstBitmap = sourceBitmap;
    }

    public void setSourceBitmapInputStream(InputStream inputStream){
        dstBitmap = BitmapFactory.decodeStream(inputStream);
    }

//  返回诸侯的图片数据
    public Bitmap getClipImage(){
//      现在画的是一个圆形的轮廓
        Bitmap haveCircleBackBitmap = Bitmap.createBitmap(canvasWidth,canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas haveCircleBackCanvas = new Canvas(haveCircleBackBitmap);
        haveCircleBackCanvas.drawARGB(255,0,0,0);
        Paint haveCircleBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        haveCircleBackPaint.setColor(Color.WHITE);
        haveCircleBackPaint.setStyle(Paint.Style.STROKE);
        haveCircleBackPaint.setStrokeWidth(5);
        haveCircleBackCanvas.drawCircle(canvasWidth/2.0f,canvasHeight/2.0f,300.0f,haveCircleBackPaint);
//      里面的圆形
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap circleBitmap = Bitmap.createBitmap(canvasWidth,canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas circleCanvas = new Canvas(circleBitmap);
        circleCanvas.drawCircle(canvasWidth/2.0f,canvasHeight/2.0f,302.0f,circlePaint);
//      扣掉里面的圆形
        Paint newPaint = new Paint();
        newPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        haveCircleBackCanvas.drawBitmap(circleBitmap,0,0,newPaint);
        newPaint.setXfermode(null);

        Paint resultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap resultBitmap = Bitmap.createBitmap(canvasWidth,canvasHeight,Bitmap.Config.ARGB_8888);

        Canvas resultCanvas = new Canvas(resultBitmap);
        resultCanvas.drawBitmap(dstBitmap,mMatrix,resultPaint);
        resultPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//取下层非交集部分
        resultCanvas.drawBitmap(haveCircleBackBitmap,0, 0,resultPaint);
        resultPaint.setXfermode(null);

//      裁剪特定的图片区域
        Bitmap resultTempBitmap = Bitmap.createBitmap(resultBitmap,canvasWidth/2-302,canvasHeight/2-302,604,604);
        haveCircleBackBitmap.recycle();
        circleBitmap.recycle();
        return resultTempBitmap;
    }
}
