package com.syudy.studyview.BallView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiwei on 2018/9/25.
 */

public class DrawPictureView extends View {

    private int preX,preY,currentX,currentY;
    private Bitmap bitmapBuffer;
    private Canvas bitmapCanvas;
    private Paint paint;
    private Path path;

    public DrawPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        path = new Path();
    }
//  控件大小发生改变的时候调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(bitmapBuffer == null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            bitmapBuffer = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapBuffer,0,0,null);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                preX = x;
                preY = y;
                path.moveTo(preX,preY);
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                path.quadTo(preX,preY,currentX,currentY);
                preX = currentX;
                preY = currentY;
                this.invalidate();//这个函数必须是在UI线程里面执行的
            case MotionEvent.ACTION_UP:
                bitmapCanvas.drawPath(path,paint);
                this.invalidate();
                break;
        }
        return true;
    }
}
