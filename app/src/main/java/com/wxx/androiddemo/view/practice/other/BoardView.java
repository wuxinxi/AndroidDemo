package com.wxx.androiddemo.view.practice.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;


/**
 * 作者：Tangren on 2019-01-21
 * 包名：com.view.practice.other
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class BoardView extends View {

    private Paint mPaint;

    private Paint textPaint;

    //view背景
    private int board_bg;

    //最里面圆以及指针的颜色
    private int board_in_circle_color;

    //最里面圆的半径
    private int board_in_circle_radius;

    //最里面圆的的stroke
    private int board_in_circle_stroke;

    //指针颜色
    private int board_pointer_color;

    //指针数量
    private int board_pointer_count;

    //文字颜色
    private int board_text_color;

    //指针stroke
    private int board_pointer_stroke;

    //文字大小
    private int board_text_size;

    private int lineColor = Color.parseColor("#2C97DF");

    private int rangeColor = Color.parseColor("#CADCEB");

    private int width = 600;
    private int height = 600;

    private float strokeWidth = 3;

    private int tikeWidth = 20;

    private RectF mRectF;

    //第二个内半圆
    private RectF secondRectF;

    private int mPercent;

    private int mScendArcWidth = 50;

    private int mMinCircleRadius = 15;

    //不断填充的弧度
    private int sweepAngle = 0;

    //刻度的个数
    private int count = 20;

    private String str;
    private int textSize = 14;
    private int textColor = Color.WHITE;
    private Rect rect;

    private Context mContext;

    public BoardView(Context context) {
        this(context, null);
    }

    public BoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(lineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);

        mRectF = new RectF();
        secondRectF = new RectF();
        rect = new Rect();

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(AppUtil.dip2px(context, textSize));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        //绘制最外层的
        mPaint.setColor(lineColor);
        mRectF.set(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);
        canvas.drawArc(mRectF, 145, 250, false, mPaint);

        //绘制内层的半圆
        mPaint.setStrokeWidth(30);
        mPaint.setColor(rangeColor);
        secondRectF.set(strokeWidth + 50, strokeWidth + 50, width - strokeWidth - 50, height - strokeWidth - 50);
        float secondRectWidth = width - strokeWidth - 50 - (strokeWidth + 50);
        float secondRectHeight = height - strokeWidth - 50 - (strokeWidth + 50);

        float percent = mPercent / 100f;

        //充满的圆弧的度数    -5是大小弧的偏差
        float fill = 250 * percent;

        //空的圆弧的度数
        float empty = 250 - fill;

        if (percent == 0) {
            mPaint.setColor(Color.WHITE);
        }
        //画粗弧突出部分左端

        mPaint.setColor(lineColor);
        canvas.drawArc(secondRectF, 135, 11, false, mPaint);
        //画粗弧的充满部分
        canvas.drawArc(secondRectF, 145, fill, false, mPaint);

        mPaint.setColor(Color.WHITE);
        //画弧胡的未充满部分
        canvas.drawArc(secondRectF, 145 + fill, empty, false, mPaint);
        //画粗弧突出部分右端
        if (percent > 0.8) {
            mPaint.setColor(Color.RED);
        }
        canvas.drawArc(secondRectF, 144 + fill + empty, 10, false, mPaint);

//        canvas.drawArc(secondRectF, 145, sweepAngle, false, mPaint);

        //最里层的圆
        mPaint.setColor(rangeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(width / 2, height / 2, mMinCircleRadius, mPaint);

        //次外层的圆
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(width / 2, height / 2, 30, mPaint);

        //绘制刻度
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawLine(width / 2, 0, height / 2, tikeWidth, mPaint);
        //旋转的角度
        float angle = 250f / count;
        //每次旋转着画，右半部分
        for (int i = 0; i < count / 2; i++) {
            canvas.rotate(angle, width / 2, height / 2);
            canvas.drawLine(width / 2, 0, width / 2, tikeWidth, mPaint);
        }
        //然后在旋转回来
        canvas.rotate(-angle * count / 2, width / 2, height / 2);

        //左半部分
        for (int i = 0; i < count / 2; i++) {
            canvas.rotate(-angle, width / 2, height / 2);
            canvas.drawLine(width / 2, 0, width / 2, tikeWidth, mPaint);

        }
        canvas.rotate(angle * count / 2, width / 2, height / 2);

        //指针
        mPaint.setColor(rangeColor);
        mPaint.setStrokeWidth(5);
        //按照百分比绘制刻度
        canvas.rotate((250 * percent - 250 / 2), width / 2, height / 2);

        canvas.drawLine(width / 2, (height / 2 - secondRectHeight / 2) + mScendArcWidth / 2 + 2, width / 2, height / 2 - mMinCircleRadius, mPaint);

        //将画布旋转回来
        canvas.rotate(-(250 * percent - 250 / 2), width / 2, height / 2);

        //绘制文字
        str = mPercent + "";
        textPaint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, width / 2 - rect.width() / 2, height - rect.height() / 2, textPaint);

    }

    private float x1;
    private float x2;

    public void setPercent(int percent) {
        mPercent = percent;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actin = event.getAction();
        switch (actin) {
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                if (x1 < x2) {
                    //右滑动
                    if (mPercent >= 100) {
                        mPercent -= 5;
                    } else mPercent += 5;
                } else if (x1 > x2) {
                    //左滑动
                    if (mPercent <= 0) {
                        mPercent += 5;
                    } else mPercent -= 5;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
        }
        return true;
    }
}
