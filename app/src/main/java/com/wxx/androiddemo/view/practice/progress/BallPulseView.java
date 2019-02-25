package com.wxx.androiddemo.view.practice.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
 * TODO:球脉冲动画View
 */
public class BallPulseView extends View {
    private int circleWidth = 100;
    private int circleHeight = 100;
    private int wrapperWidth;
    private int space = 20;
    private int count = 3;
    private int color = Color.RED;
    private Paint paint;

    private static final float SCALE = 1.0f;
    private float[] scales = new float[]{SCALE, SCALE, SCALE};

    public BallPulseView(Context context) {
        this(context, null);
    }

    public BallPulseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallPulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;

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
            setMeasuredDimension(wrapperWidth, circleHeight + space * 2);
        } else if (specModeWidth == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wrapperWidth, specSizeHeight);
        } else if (specModeHeight == MeasureSpec.AT_MOST) {
            setMeasuredDimension(specSizeWidth, circleHeight + space * 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //view总宽度
        float viewWidth = (count - 1) * space + circleWidth * count;
        //cx起始位置（总宽度-view总宽度+单个view/2）
        float startX = wrapperWidth / 2 - viewWidth / 2 + circleWidth / 2;
        float startY = circleHeight / 2 + space;
        for (int i = 0; i < count; i++) {
            canvas.save();
            canvas.translate(startX, startY);
            canvas.scale(scales[i], scales[i]);
            canvas.translate(-startX, -startY);
            canvas.drawCircle(startX, startY, circleWidth / 2, paint);
            startX += space + circleWidth;
            canvas.restore();
        }

    }

    private List<ValueAnimator> valueAnimatorList() {
        List<ValueAnimator> list = new ArrayList<>();
        int[] delays = new int[]{120, 360, 720};
        for (int i = 0; i < count; i++) {
            ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1f, 0.3f, 1f);
            scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnimator.setStartDelay(delays[i]);
            scaleAnimator.setDuration(800);
            list.add(scaleAnimator);
            final int index = i;
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scales[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }
        return list;
    }
}
