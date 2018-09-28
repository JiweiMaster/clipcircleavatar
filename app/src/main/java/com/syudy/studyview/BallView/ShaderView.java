package com.syudy.studyview.BallView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiwei on 2018/9/25.
 */

public class ShaderView extends View {
    private Paint paint;
    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50f);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
        paint.setShadowLayer(5,2,2,Color.RED);
        canvas.drawText("Android开发",100,100,paint);
    }
}
