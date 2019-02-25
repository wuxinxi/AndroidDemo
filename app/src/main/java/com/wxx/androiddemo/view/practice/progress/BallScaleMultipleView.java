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
public class BallScaleMultipleView extends View {
    private Paint paint;
    private int width = 200;
    private int wrapperWidth;
    private int color = Color.RED;
    private Rect bounds;
    private int space = 20;
    private float[] scales = new float[]{1, 1, 1};
    private int[] alphas = new int[]{255, 255, 255};

    public BallScaleMultipleView(Context context) {
        this(context, null);
    }

    public BallScaleMultipleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallScaleMultipleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
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
        for (int i = 0; i < 3; i++) {
            paint.setAlpha(alphas[i]);
            canvas.scale(scales[i],scales[i],wrapperWidth / 2, space + width / 2);
            canvas.drawCircle(wrapperWidth / 2 , space + width / 2, width / 2, paint);
        }
    }

    private List<ValueAnimator> valueAnimators() {
        List<ValueAnimator> list = new ArrayList<>();
        long[] delays = new long[]{0, 200, 400};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnimator = ValueAnimator.ofFloat(0, 1f);
            scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnimator.setDuration(1000);
            scaleAnimator.setStartDelay(delays[index]);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scales[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            ValueAnimator alphaAnimator = ValueAnimator.ofInt(255, 0);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setDuration(1000);
            alphaAnimator.setStartDelay(delays[index]);
            alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            list.add(scaleAnimator);
            list.add(alphaAnimator);
        }
        return list;
    }
}
