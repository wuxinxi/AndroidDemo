package com.wxx.androiddemo.view.practice.path;

import android.animation.AnimatorSet;
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
 * 作者：Tangren on 2019-01-17
 * 包名：com.view.practice.path
 * 邮箱：996489865@qq.com
 * TODO:暂停播放
 */
public class TrianglePauseButton extends View implements View.OnClickListener {
    //绘制三角形
    private Paint paint;
    private Path path;

    //绘制矩形
    private Paint paintRect;
    private Path pathRect;

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

    private int alpha = 255;
    private int alphaRect;

    private AtomicBoolean isPause = new AtomicBoolean(true);


    public TrianglePauseButton(Context context) {
        this(context, null);
    }

    public TrianglePauseButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrianglePauseButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();

        paintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathRect = new Path();

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(btnColor);
        paintRect.setColor(btnColor);

        //间隔
        float distance = midSpace * (1 - progress);
        //两边长方形宽度
        float recWidth = (squareWidth - distance) / 2;

        //左边左上角X轴位置
        float leftLeftTopX = centerX - squareWidth / 2;
        //左边左上角Y轴位置
        float leftLeftTopY = centerY - squareHeight / 2;

//        if (isPause.get()) {
        //绘制三角形
        path.moveTo(leftLeftTopX + squareWidth / 4, leftLeftTopY);
        path.lineTo(centerX + squareWidth / 2, centerY);
        path.lineTo(leftLeftTopX + squareWidth / 4, centerY + squareHeight / 2);
        path.close();
//        } else {
        //绘制左边
        //绘制顺序：顺时针
        pathRect.moveTo(leftLeftTopX, leftLeftTopY);
        pathRect.lineTo(leftLeftTopX + recWidth, leftLeftTopY);
        pathRect.lineTo(leftLeftTopX + recWidth, leftLeftTopY + squareHeight);
        pathRect.lineTo(leftLeftTopX, leftLeftTopY + squareHeight);
        pathRect.close();

        //绘制右边
        float rightLeftTopX = distance / 2 + centerX;
        float rightLeftTopY;
        rightLeftTopY = leftLeftTopY;
        pathRect.moveTo(rightLeftTopX, rightLeftTopY);
        pathRect.lineTo(rightLeftTopX + recWidth, rightLeftTopY);
        pathRect.lineTo(rightLeftTopX + recWidth, rightLeftTopY + squareHeight);
        pathRect.lineTo(rightLeftTopX, rightLeftTopY + squareHeight);
        pathRect.close();
//        }

        //默认播放显示播放【三角形】
        paint.setAlpha(alpha);
        canvas.drawPath(path, paint);

        paintRect.setAlpha(alphaRect);
        canvas.drawPath(pathRect, paintRect);
    }

    @Override
    public void onClick(View v) {
        startAnimator();

    }


    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(isPause.get() ? 255 : 0, isPause.get() ? 0 : 255);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                path.reset();
                paint.reset();
                invalidate();
            }
        });
        animator.setDuration(200);

        ValueAnimator animatorRect = ValueAnimator.ofInt(isPause.get() ? 0 : 255, isPause.get() ? 255 : 0);
        animatorRect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alphaRect = (int) animation.getAnimatedValue();
                pathRect.reset();
                paintRect.reset();
                invalidate();
            }
        });
        animatorRect.setDuration(200);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(isPause.get() ? animator : animatorRect, isPause.get() ? animatorRect : animator);
        set.start();

        isPause.set(!isPause.get());
    }

}
