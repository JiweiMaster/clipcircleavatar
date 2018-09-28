package com.syudy.studyview.BallView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiwei on 2018/9/25.
 */

public class PorterDuffxerView extends View {
    public PorterDuffxerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        1--创建三个位图
        Bitmap dst = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_8888);
        Bitmap src = dst.copy(Bitmap.Config.ARGB_8888,true);
        Bitmap b3 = Bitmap.createBitmap(450,450, Bitmap.Config.ARGB_8888);
        //创建三个画布放在三个图片上
        Canvas c1 = new Canvas(dst);
        Canvas c2 = new Canvas(src);
        Canvas c3 = new Canvas(b3);

        Paint p1 = new Paint();
        p1.setColor(Color.GRAY);
        c1.drawCircle(150,150,150,p1);

        Paint p2 = new Paint();
        p2.setColor(Color.GREEN);
        c2.drawRect(0,0,300,300,p2);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        c3.drawColor(Color.LTGRAY);
//        2--创建Layer并且入栈************************************************important*******************************
        int Layer = c3.saveLayer(150,150,450,450,null,Canvas.ALL_SAVE_FLAG);
//        3--绘制DST
        c3.drawBitmap(dst,0,0,null);
//        4--设置位图的运算模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//        5--画SRC
        c3.drawBitmap(src,150,150,paint);

//        6--清除位图的运算模式
        paint.setXfermode(null);
//        7--Layer出栈
        c3.restoreToCount(Layer);
//        8--结果绘制到canvas上面
        canvas.drawBitmap(b3,0,0,null);

    }
}
