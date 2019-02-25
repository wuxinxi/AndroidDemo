package com.wxx.androiddemo.view.practice.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
 * TODO:正方形翻转(左右上下)
 */
public class SquareSpinView extends View {
    private Paint paint;
    private int width = 200;
    private int wrapperWidth;
    private int color = Color.RED;
    private Rect bounds;
    private int space = 20;

    private float rotateX;
    private float rotateY;

    private Camera mCamera;
    private Matrix mMatrix;

    public SquareSpinView(Context context) {
        this(context, null);
    }

    public SquareSpinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareSpinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        wrapperWidth = AppUtil.getMetrics(context).widthPixels;
        bounds = new Rect(wrapperWidth / 2 - width / 2, space, wrapperWidth / 2 + width / 2, space + width);
        mCamera = new Camera();
        mMatrix = new Matrix();

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
        mCamera.save();
        mMatrix.reset();
        mCamera.rotateX(rotateX);
        mCamera.rotateY(rotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-(wrapperWidth / 2 ), -(space + width / 2));
        mMatrix.postTranslate(wrapperWidth / 2 , space + width / 2);

        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawRect(bounds, paint);
        canvas.restore();
    }

    private List<ValueAnimator> valueAnimators() {
        List<ValueAnimator> list = new ArrayList<>();
        ValueAnimator animatorX = ValueAnimator.ofFloat(0, 180f, 180f, 0,0);
        animatorX.setDuration(2000);
        animatorX.setRepeatCount(ValueAnimator.INFINITE);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator animatorY = ValueAnimator.ofFloat(0, 0, 180f, 180f,0);
        animatorY.setDuration(2000);
        animatorY.setRepeatCount(ValueAnimator.INFINITE);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateY = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        list.add(animatorX);
        list.add(animatorY);
        return list;
    }

}
