package com.wxx.androiddemo.view.practice.progress;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wxx.androiddemo.view.practice.util.AppUtil;


/**
 * 作者：Tangren on 2019-01-16
 * 包名：com.view.practice.progress
 * 邮箱：996489865@qq.com
 * TODO:打钩进度条
 */
public class CircleProgressTickBar extends View implements View.OnClickListener {


    private Paint paint;
    private int color = Color.RED;
    //默认宽度
    private int defaultWidht = 100;
    private int defalutHeight = 100;
    private int radius;
    //屏幕宽度
    private int wrapWidth;
    private int wrapHeight;

    private float centerX;
    private float centerY;

    //进度
    private float progress;
    private RectF rectF;
    private Path path;


    float left;
    float top;
    float right;
    float bottom;


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }


    public CircleProgressTickBar(Context context) {
        this(context, null);
    }

    public CircleProgressTickBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressTickBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultWidht = AppUtil.dip2px(context, defaultWidht);
        defalutHeight = AppUtil.dip2px(context, defalutHeight);
        radius = defaultWidht / 2;
        wrapWidth = AppUtil.getMetrics(context).widthPixels;
        wrapHeight = AppUtil.getMetrics(context).heightPixels;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTextSize(AppUtil.dip2px(context, 25));
        paint.setTextAlign(Paint.Align.CENTER);

        left = wrapWidth / 2 - defaultWidht / 2;
        top = wrapHeight / 2 - defalutHeight / 2;
        right = wrapWidth / 2 + defaultWidht / 2;
        bottom = wrapHeight / 2 + defalutHeight / 2;
        rectF = new RectF(left, top, right, bottom);

        centerX = left + defaultWidht / 2;
        centerY = top + defalutHeight / 2;

        path = new Path();

        //启动动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 360f);
        animator.setDuration(1000);
        animator.start();

        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(25);
        canvas.drawArc(rectF, 0, progress, false, paint);
        if (progress >= 360) {

            //打钩
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            paint.setStrokeCap(Paint.Cap.ROUND);

            int inLeft = (int) (left + 60);
            int inTop = (int) (top + 80);
            int inRight = (int) (right - 60);
            int inBottom = (int) (bottom - 80);
            //辅助测试
//            Rect rect = new Rect(inLeft, inTop, inRight, inBottom);
//            canvas.drawRect(rect, paint);

            float startX = inLeft;
            float statY = inBottom - (inBottom - inTop) / 2;
            path.moveTo(startX, statY);

            startX = inLeft + (inRight - inLeft) / 2;
            statY = inBottom;
            path.lineTo(startX, statY);

            startX = inRight;
            statY = inTop;
            path.lineTo(startX, statY);
            canvas.drawPath(path, paint);

        }
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 360f);
        animator.setDuration(1000);
        animator.start();

    }
}