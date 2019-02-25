package com.wxx.androiddemo.view.practice.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
 * TODO:一句话描述
 */
public class BallSpinFadeLoaderView extends View {
    private Paint paint;
    private int width = 300;
    private int wrapperWidth;
    private int color = Color.RED;
    private int space = 20;
    private int count = 10;
    Rect rect;
    private int alpha[] = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    private float SCALE = 1.0f;
    private float scales[] = {SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE};

    public BallSpinFadeLoaderView(Context context) {
        this(context, null);
    }

    public BallSpinFadeLoaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallSpinFadeLoaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
        rect = new Rect(wrapperWidth / 2 - width / 2, space, wrapperWidth / 2 + width / 2, width + space);

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

        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.translate(wrapperWidth / 2, space + width / 2);
        for (int i = 0; i < count; i++) {
            paint.setAlpha(alpha[i]);
            canvas.drawLine(width / 2 - 30, 0, width / 2, 0, paint);
            canvas.rotate(360/count);
        }
    }


    private List<ValueAnimator> valueAnimators() {
        List<ValueAnimator> list = new ArrayList<>();
        int delays[] = {0, 120, 240, 360, 480, 600, 720, 840, 960, 1180};
        for (int i = 0; i < alpha.length; i++) {
            final int index = i;
            ValueAnimator alphaAnimator = ValueAnimator.ofInt(255, 80, 255);
            alphaAnimator.setDuration(1000);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setStartDelay(delays[i]);
            alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            list.add(alphaAnimator);

            ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
            scaleAnimator.setStartDelay(delays[i]);
            scaleAnimator.setDuration(1000);
            scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scales[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            list.add(scaleAnimator);
        }
        return list;
    }

}
