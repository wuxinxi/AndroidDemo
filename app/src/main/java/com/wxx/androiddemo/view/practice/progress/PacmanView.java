package com.wxx.androiddemo.view.practice.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: TangRen on 2019/1/20
 * 包名：com.view.practice.progress
 * 邮箱：996489865@qq.com
 * TODO:吃豆人
 */
public class PacmanView extends View {
    private Paint paint;
    private int width = 120;
    private int wrapperWidth;
    private int circleWidth = 30;
    private int color = Color.RED;
    private Rect bounds;
    private int space = 40;
    private RectF rectF;

    private int topDegree = 0;
    private int bottomDegree = 0;

    private int alpha = 255;
    private float translateX;


    public PacmanView(Context context) {
        this(context, null);
    }

    public PacmanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PacmanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;

        float left = wrapperWidth / 2 - width / 2;
        float top = space;
        float right = wrapperWidth / 2 + width / 2;
        float bottom = width + space;
        rectF = new RectF(left, top, right, bottom);

        List<ValueAnimator> valueAnimators = valueAnimators();
        for (ValueAnimator valueAnimator : valueAnimators) {
            valueAnimator.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (specModeWidth == MeasureSpec.AT_MOST && specModeHeight == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wrapperWidth, width + space * 2);
        } else if (specModeWidth == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wrapperWidth, specSizeHeight);
        } else if (specModeHeight == MeasureSpec.AT_MOST) {
            setMeasuredDimension(specSizeWidth, width + space * 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制底部实心圆
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);

        //绘制上部关合动画
        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF, 0, topDegree, true, paint);

        //绘制下部关合动画
        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF, 0, bottomDegree, true, paint);


        //绘制豆子
        paint.setColor(Color.RED);
        paint.setAlpha(alpha);
        canvas.translate(translateX,space + width / 2);
        //cx总宽度/2+控件宽度/2+豆子width/2
        //在translate基础上绘制
        canvas.drawCircle(0, 0, circleWidth / 2, paint);

    }

    private List<ValueAnimator> valueAnimators() {
        List<ValueAnimator> list = new ArrayList<>();
        ValueAnimator scanAnimator = ValueAnimator.ofInt(0, -45);
        scanAnimator.setDuration(750);
        scanAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scanAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                topDegree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(scanAnimator);
        ValueAnimator scanAnimator2 = ValueAnimator.ofInt(0, 45);
        scanAnimator2.setDuration(750);
        scanAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        scanAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bottomDegree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(scanAnimator2);
        ValueAnimator alphaAnimator = ValueAnimator.ofInt(255, 0);
        alphaAnimator.setDuration(750);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(alphaAnimator);
        ValueAnimator transAnimator = ValueAnimator.ofFloat(wrapperWidth / 2 + width / 2, wrapperWidth / 2+circleWidth);
        transAnimator.setDuration(750);
        transAnimator.setRepeatCount(ValueAnimator.INFINITE);
        transAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(transAnimator);
        return list;
    }
}
