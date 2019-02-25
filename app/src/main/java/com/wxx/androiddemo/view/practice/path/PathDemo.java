package com.wxx.androiddemo.view.practice.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者：Tangren on 2019-01-17
 * 包名：com.view.practice.path
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class   PathDemo extends View implements View.OnClickListener, View.OnLongClickListener {

    Path path;
    Paint paint;

    int width = 200;
    int wrapWidth;
    int wrapHeight;

    int len = 100;

    public PathDemo(Context context) {
        this(context, null);
    }

    public PathDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        path = new Path();

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        wrapWidth = metrics.widthPixels;
        wrapHeight = metrics.heightPixels;


        setOnClickListener(this);
        setOnLongClickListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(wrapWidth / 2, wrapHeight / 2);
        path.lineTo(wrapWidth / 2, wrapHeight / 2 + len);
        path.lineTo(wrapWidth / 2 + len * 2, wrapHeight / 2 + len);
        path.close();
        canvas.drawPath(path, paint);

        path.moveTo(20, 20);
        path.rLineTo(200, 0);
        path.rLineTo(0, 300);
        path.lineTo(20,320);
        path.close();

        canvas.drawPath(path,paint);

    }

    @Override
    public void onClick(View v) {
        len += 10;
        invalidate();
    }

    @Override
    public boolean onLongClick(View v) {
        path.reset();
        len -= 20;
        invalidate();

        System.out.println("len = " + len);
        return true;
    }
}
