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
 * 作者: TangRen on 2019/1/19
 * 包名：com.view.practice.progress
 * 邮箱：996489865@qq.com
 * TODO:球三角形路径
 */
public class BallTrianglePathView extends View {
    private Paint paint;
    private int wrapperWidth;
    private int width = 200;
    private int circleWidth = 50;
    private int color = Color.RED;
    private int space = 20;
    Rect rect;

    private float transX;
    private float transY;

    private float transX2;
    private float transY2;

    private float transX3;
    private float transY3;

    public BallTrianglePathView(Context context) {
        this(context, null);
    }

    public BallTrianglePathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallTrianglePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;

        rect = new Rect(wrapperWidth / 2 - width / 2, space, wrapperWidth / 2 + width / 2, width + space);
        transX = wrapperWidth / 2;
        transY = space + circleWidth / 2;

        transX2 = wrapperWidth / 2 + width / 2 - circleWidth / 2;
        transY2 = space + width - circleWidth / 2;

        transX3 = wrapperWidth / 2 - width / 2 + circleWidth / 2;
        transY3 = transY2;

        List<ValueAnimator> animators = animators();
        for (ValueAnimator animator : animators) {
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
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(rect, paint);

        paint.setColor(color);
        float cx = wrapperWidth / 2;
        float cy = space + circleWidth / 2;
        canvas.save();
        canvas.drawCircle(transX, transY, circleWidth / 2, paint);
        canvas.restore();

        //底部right
        cx = wrapperWidth / 2 + width / 2 - circleWidth / 2;
        cy = space + width - circleWidth / 2;
        canvas.save();
        canvas.drawCircle(transX2, transY2, circleWidth / 2, paint);
        canvas.restore();

        //底部left
        cx = wrapperWidth / 2 - width / 2 + circleWidth / 2;
        canvas.save();
        canvas.drawCircle(transX3, transY3, circleWidth / 2, paint);
        canvas.restore();

    }

    private List<ValueAnimator> animators() {
        List<ValueAnimator> list = new ArrayList<>();
        ValueAnimator translationX1 = ValueAnimator.ofFloat(wrapperWidth / 2, wrapperWidth / 2 + width / 2 - circleWidth / 2);
        translationX1.setDuration(800);
        translationX1.setRepeatCount(ValueAnimator.INFINITE);
        translationX1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator translationY1 = ValueAnimator.ofFloat(space + circleWidth / 2, space + width - circleWidth / 2);
        translationY1.setDuration(800);
        translationY1.setRepeatCount(ValueAnimator.INFINITE);
        translationY1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transY = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(translationX1);
        list.add(translationY1);

        ValueAnimator translationX2 = ValueAnimator.ofFloat(wrapperWidth / 2 + width / 2 - circleWidth / 2, wrapperWidth / 2 - width / 2 + circleWidth / 2);
        translationX2.setDuration(800);
        translationX2.setRepeatCount(ValueAnimator.INFINITE);
        translationX2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transX2 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator translationY2 = ValueAnimator.ofFloat(space + width - circleWidth / 2, space + width - circleWidth / 2);
        translationY2.setDuration(800);
        translationY2.setRepeatCount(ValueAnimator.INFINITE);
        translationY2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transY2 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        list.add(translationX2);
        list.add(translationY2);


        ValueAnimator translationX3 = ValueAnimator.ofFloat(wrapperWidth / 2 - width / 2 + circleWidth / 2, wrapperWidth / 2);
        translationX3.setDuration(800);
        translationX3.setRepeatCount(ValueAnimator.INFINITE);
        translationX3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transX3 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator translationY3 = ValueAnimator.ofFloat(space + width - circleWidth / 2, space + circleWidth / 2);
        translationY3.setDuration(800);
        translationY3.setRepeatCount(ValueAnimator.INFINITE);
        translationY3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transY3 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        list.add(translationX3);
        list.add(translationY3);
        return list;
    }
}
