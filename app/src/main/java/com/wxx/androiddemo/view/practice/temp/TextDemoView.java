package com.wxx.androiddemo.view.practice.temp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;


/**
 * 作者：Tangren on 2019-01-23
 * 包名：com.view.practice.temp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class TextDemoView extends View {
    private Paint paint;
    private Path path;
    private int wrapperWidth;
    private int wrapperHeight;

    public TextDemoView(Context context) {
        this(context, null);
    }

    public TextDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
        wrapperHeight = AppUtil.getMetrics(context).heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(wrapperWidth/2,wrapperHeight/2);

        canvas.rotate(90);
        paint.setTextSize(80);
        canvas.drawText("12",-80/2,-(paint.ascent()+paint.descent())/2,paint);
    }
}
