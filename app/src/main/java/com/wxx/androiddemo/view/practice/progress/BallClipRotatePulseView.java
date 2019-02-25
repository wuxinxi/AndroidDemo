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
 * 作者: TangRen on 2019/1/19
 * 包名：com.view.practice.progress
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class BallClipRotatePulseView extends View {

    private Paint paint;
    private int width = 200;
    private int wrapperWidth;
    private int space = 20;
    private int strokeWidth = 10;
    private int outColor = Color.RED;
    private int inColor = Color.RED;
    private RectF bounds;
    private Rect rect;

    private int leftDegree = 135;
    private int rightDegree = 45;

    private float scale = 0;


    public BallClipRotatePulseView(Context context) {
        this(context, null);
    }

    public BallClipRotatePulseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallClipRotatePulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(outColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
        float left = wrapperWidth / 2 - width / 2;
        float top = space;
        float right = wrapperWidth / 2 + width / 2;
        float bottom = top + width;
        bounds = new RectF(left, top, right, bottom);

        List<ValueAnimator> list = valueAnimators();
        for (ValueAnimator animator : list) {
            animator.start();
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

        paint.setStyle(Paint.Style.STROKE);
//        canvas.scale(scale, scale, wrapperWidth / 2, width / 2 + space);//外层缩放
        canvas.drawArc(bounds, leftDegree, 90, false, paint);
//        canvas.scale(scale, scale, wrapperWidth / 2, width / 2 + space);//外层缩放
        canvas.drawArc(bounds, rightDegree, -90, false, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.scale(scale, scale, wrapperWidth / 2, width / 2 + space);
        canvas.drawCircle(wrapperWidth / 2, width / 2 + space, width / 4, paint);
    }

    private List<ValueAnimator> valueAnimators() {
        List<ValueAnimator> list = new ArrayList<>();
        ValueAnimator rotateAnimator1 = ValueAnimator.ofInt(leftDegree, 495);
        rotateAnimator1.setDuration(800);
        rotateAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                leftDegree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator rotateAnimator2 = ValueAnimator.ofInt(rightDegree, 405);
        rotateAnimator2.setDuration(800);
        rotateAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rightDegree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator translationAnimator1 = ValueAnimator.ofFloat(1f, 0.6f, 1f);
        translationAnimator1.setDuration(800);
        translationAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(rotateAnimator1);
        list.add(rotateAnimator2);
        list.add(translationAnimator1);
        return list;
    }
}
