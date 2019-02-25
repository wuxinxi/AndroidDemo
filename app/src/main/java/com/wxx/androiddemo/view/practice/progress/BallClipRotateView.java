package com.wxx.androiddemo.view.practice.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
 * TODO:旋转球View
 */
public class BallClipRotateView extends View {
    private Paint paint;
    private int width = 200;
    private int wrapperWidth;
    private int color = Color.RED;
    private RectF bounds;
    private int space = 20;
    private int strokeWidth = 10;
    private int progress = 0;
    private float scale = 1f;


    public BallClipRotateView(Context context) {
        this(context, null);
    }

    public BallClipRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallClipRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);

        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
        float left = wrapperWidth / 2 - width / 2;
        float top = space;
        float right = wrapperWidth / 2 + width / 2;
        float bottom = top + width;
        bounds = new RectF(left, top, right, bottom);

        List<ValueAnimator> list = valueAnimatorList();
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
        canvas.scale(scale, scale, wrapperWidth / 2, width / 2);
        canvas.drawArc(bounds, progress, 300, false, paint);
    }

    private List<ValueAnimator> valueAnimatorList() {
        List<ValueAnimator> list = new ArrayList<>();
        ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 360);
        rotateAnimator.setDuration(800);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1f, 0.6f, 1f);
        scaleAnimator.setDuration(800);
        scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(rotateAnimator);
        list.add(scaleAnimator);
        return list;
    }
}
