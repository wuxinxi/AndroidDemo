package com.wxx.androiddemo.view.practice.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 作者：Tangren on 2019-01-16
 * 包名：com.view.practice.path
 * 邮箱：996489865@qq.com
 * TODO:方形播放暂停动画
 */
public class SquarePauseButton extends View implements View.OnClickListener, View.OnLongClickListener {
    private Paint paint;
    private Path path;
    private int width = 100;
    private int height;
    private int radius;
    private int squareWidth;
    private int squareHeight;
    private int bgColor = Color.parseColor("#C0C0C0");
    private int btnColor = Color.BLUE;

    private int wrapWidth;
    private int warpHeight;

    private float centerX;
    private float centerY;

    //间隔
    private float midSpace = 40;
    private float progress = 0;

    private AtomicBoolean isPause = new AtomicBoolean(true);


    public SquarePauseButton(Context context) {
        this(context, null);
    }

    public SquarePauseButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquarePauseButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        width = AppUtil.dip2px(context, width);
        height = width;
        radius = width / 2;
        squareWidth = width / 2;
        squareHeight = squareWidth;

        wrapWidth = AppUtil.getMetrics(context).widthPixels;
        warpHeight = AppUtil.getMetrics(context).heightPixels;
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);

        centerX = wrapWidth / 2;
        centerY = warpHeight / 2;
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(btnColor);

        //间隔
        float distance = midSpace * (1 - progress);
        //两边长方形宽度
        float recWidth = (squareWidth - distance) / 2;

        //左边左上角X轴位置
        float leftLeftTopX = centerX - squareWidth / 2;
        //左边左上角Y轴位置
        float leftLeftTopY = centerY - squareHeight / 2;

        //绘制左边
        //绘制顺序：顺时针
        path.moveTo(leftLeftTopX, leftLeftTopY);
        path.lineTo(leftLeftTopX + recWidth, leftLeftTopY);
        path.lineTo(leftLeftTopX + recWidth, leftLeftTopY + squareHeight);
        path.lineTo(leftLeftTopX, leftLeftTopY + squareHeight);
        path.close();

        //绘制右边
        float rightLeftTopX = distance / 2 + centerX;
        float rightLeftTopY;
        rightLeftTopY = leftLeftTopY;
        path.moveTo(rightLeftTopX, rightLeftTopY);
        path.lineTo(rightLeftTopX + recWidth, rightLeftTopY);
        path.lineTo(rightLeftTopX + recWidth, rightLeftTopY + squareHeight);
        path.lineTo(rightLeftTopX, rightLeftTopY + squareHeight);
        path.close();

        canvas.drawPath(path, paint);

    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofFloat(isPause.get() ? 0 : 1f, isPause.get() ? 1f : 0);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                path.reset();
                progress = (float) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator.start();
        isPause.set(!isPause.get());
    }

    @Override
    public boolean onLongClick(View v) {
        path.reset();
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                path.reset();
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
        return true;
    }
}
