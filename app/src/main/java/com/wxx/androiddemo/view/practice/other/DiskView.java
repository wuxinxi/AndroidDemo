package com.wxx.androiddemo.view.practice.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者：Tangren on 2019-01-21
 * 包名：com.view.practice.other
 * 邮箱：996489865@qq.com
 * TODO:圆盘view
 */
public class DiskView extends View {
    private Paint paint;
    private Path path;
    private Rect rect;
    private RectF bounds;
    private RectF inBounds;
    private int width = 600;
    private int height = 650;
    private int inWidth = 400;
    private int circleWidth = 150;
    private int outStrokeWidth = 10;
    private int inStrokeWidth = 50;
    private int wrapperWidth;
    private int space = 20;

    public DiskView(Context context) {
        this(context, null);
    }

    public DiskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        wrapperWidth = metrics.widthPixels;
        rect = new Rect(wrapperWidth / 2 - width / 2, space, wrapperWidth / 2 + width / 2, space + height);
        bounds = new RectF(wrapperWidth / 2 - width / 2, space, wrapperWidth / 2 + width / 2, space + width);
        inBounds = new RectF(wrapperWidth / 2 - inWidth / 2, space + width / 2 - inWidth / 2, wrapperWidth / 2 + inWidth / 2, space + inWidth + width / 2 - inWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制底层
        paint.setColor(Color.parseColor("#C0C0C0"));
        canvas.drawRect(rect, paint);
        //绘制外圆
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outStrokeWidth);
        canvas.drawArc(bounds, 135, 270, false, paint);

        //绘制内圆
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(inStrokeWidth);
        canvas.drawArc(inBounds, 135, 270, false, paint);

        //绘制内部圆环
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(7);
        canvas.drawCircle(wrapperWidth / 2, space + width / 2, circleWidth / 2, paint);


        //绘制最内部
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        canvas.drawLine(wrapperWidth / 2, space + width / 2 - circleWidth / 4, wrapperWidth / 2, space + width / 2 - inWidth / 2 + inStrokeWidth / 2 + 20, paint);

        paint.setStrokeWidth(20);
        canvas.drawCircle(wrapperWidth / 2, space + width / 2, circleWidth / 4, paint);

        drawDegree(canvas);
    }


    private int len = 30;
    private int radius = width / 2;
    private int swipeDegree = 270;
    private int count = 20;


    private void drawDegree(Canvas canvas) {
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);

        //旋转135° 移动到圆心
        canvas.translate(wrapperWidth / 2, space+width/2);
        canvas.rotate(135);

        //需要绘制最后一个,所以需要count+1
        for (int i = 0; i < count+1; i++) {
            if (i==count){
                //最后一个需要特殊处理
                canvas.drawLine(radius,-paint.getStrokeWidth()/2, radius - len, -paint.getStrokeWidth()/2, paint);
                canvas.rotate((float) swipeDegree / count);
            }else {
                canvas.drawLine(radius,paint.getStrokeWidth()/2, radius - len, paint.getStrokeWidth()/2, paint);
                canvas.rotate((float) swipeDegree / count);
            }

        }

    }

}
