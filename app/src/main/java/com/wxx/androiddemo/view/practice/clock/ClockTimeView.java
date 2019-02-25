package com.wxx.androiddemo.view.practice.clock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;


/**
 * 作者：Tangren on 2019-01-21
 * 包名：com.view.practice.clock
 * 邮箱：996489865@qq.com
 * TODO:表
 */
public class ClockTimeView extends View {
    private Paint paint;
    private Path path;
    private int width;
    private int wrapperWidth;
    private int wrapperHeight;
    private int circleWidth;
    private int minStrokeWidth = 20;
    private int secondStrokeWidth = 10;
    private int minLen = 40;
    private int secondLen = 25;
    private int pointerCount = 60;
    private int bgColor = Color.parseColor("#DE9B5D");

    private int hourPointerLen;
    private int hourPointerStrokeWidth = 30;
    private int hourPointerColor = Color.parseColor("#009ACD");

    private int minPointerLen;
    private int minPointerStrokeWidth = 15;
    private int minPointerColor = Color.parseColor("#008B8B");

    private int secondPointerLen;
    private int secondPointerStrokeWidth = 5;
    private int secondPointerColor = Color.parseColor("#68228B");

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.postDelayed(runnable, 1000);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            degress += 6;
            if (degress % 360 == 0) {
                minDegree += 6;
                hourDegree+=1;
            }
            invalidate();
            handler.sendEmptyMessage(0);
        }
    };

    public ClockTimeView(Context context) {
        this(context, null);
    }

    public ClockTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        width = AppUtil.dip2px(context, 400);
        circleWidth = AppUtil.dip2px(context, 30);
        int radius = width / 2;
        secondPointerLen = radius - 150;
        hourPointerLen = radius / 2;
        minPointerLen = radius - 200;
        DisplayMetrics metrics = AppUtil.getMetrics(context);
        wrapperWidth = metrics.widthPixels;
        wrapperHeight = metrics.heightPixels;
        minStrokeWidth = AppUtil.px2dip(context, minStrokeWidth);
        secondStrokeWidth = AppUtil.px2dip(context, secondStrokeWidth);

        handler.sendEmptyMessage(0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        paint.setColor(bgColor);
        canvas.drawCircle(wrapperWidth / 2, wrapperHeight / 2, width / 2, paint);
        paint.setColor(Color.parseColor("#AD7E52"));
        canvas.drawCircle(wrapperWidth / 2, wrapperHeight / 2, circleWidth / 2, paint);
        canvas.restore();

        drawScale(canvas);

        drawPointer(canvas);
    }

    float degress = 30;
    float minDegree = 0;
    float hourDegree = 30;

    private void drawPointer(Canvas canvas) {

        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(hourPointerStrokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(hourPointerColor);
        canvas.translate(wrapperWidth / 2, wrapperHeight / 2);
        canvas.rotate(hourDegree);
        canvas.drawLine(0, 0, hourPointerLen, 0, paint);
        canvas.translate(-wrapperWidth / 2, -wrapperHeight / 2);
        canvas.restore();

        canvas.save();
        paint.setStrokeWidth(minPointerStrokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(minPointerColor);
        canvas.translate(wrapperWidth / 2, wrapperHeight / 2);
        canvas.rotate(minDegree);
        canvas.drawLine(0, 0, 0, -minPointerLen, paint);
        canvas.translate(-wrapperWidth / 2, -wrapperHeight / 2);
        canvas.restore();

        canvas.save();
        paint.setStrokeWidth(secondPointerStrokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(secondPointerColor);
        canvas.translate(wrapperWidth / 2, wrapperHeight / 2);
        canvas.rotate(degress);
        canvas.drawLine(0, 0, 0, secondPointerLen, paint);
        canvas.translate(-wrapperWidth / 2, -wrapperHeight / 2);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(wrapperWidth / 2, wrapperHeight / 2);
        canvas.rotate(-90);
        paint.setTextSize(80);
        for (int i = 0; i < pointerCount; i++) {
            paint.setColor(Color.BLACK);
            if (i % 5 == 0) {
                paint.setStrokeWidth(minStrokeWidth);
                canvas.drawLine(width / 2 - minLen, 0, width / 2, 0, paint);
                String num = i == 0 ? 12 + "" : i / 5 + "";
                paint.setColor(Color.RED);
                if (i == 0) {
                    canvas.save();
                    canvas.rotate(90, width / 2 - minLen - 80 * num.length() / 2 - 10, -(paint.ascent() + paint.descent()) / 2);
                    canvas.drawText(num, width / 2 - minLen - 80 * num.length() / 2 - 80, 0, paint);
                    canvas.restore();
                } else {
                    canvas.drawText(num, width / 2 - minLen - 80 * num.length() / 2 - 10, -(paint.ascent() + paint.descent()) / 2, paint);
                }

            } else {
                paint.setStrokeWidth(secondStrokeWidth);
                canvas.drawLine(width / 2 - secondLen, 0, width / 2, 0, paint);
            }

            canvas.rotate(360 / 60);
        }
        canvas.restore();
    }
}

