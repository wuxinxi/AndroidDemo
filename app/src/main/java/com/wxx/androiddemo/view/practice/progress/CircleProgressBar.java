package com.wxx.androiddemo.view.practice.progress;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.wxx.androiddemo.view.practice.util.AppUtil;


/**
 * 作者：Tangren on 2019-01-16
 * 包名：com.view.practice.progress
 * 邮箱：996489865@qq.com
 * TODO:圆形进度
 */
public class CircleProgressBar extends View implements OnClickListener,
        View.OnTouchListener {

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

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultWidht = AppUtil.dip2px(context, defaultWidht);
        defalutHeight = AppUtil.dip2px(context, defalutHeight);
        radius = defaultWidht / 2;
        wrapWidth = AppUtil.getMetrics(context).widthPixels;
        wrapHeight = AppUtil.getMetrics(context).heightPixels;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTextSize(AppUtil.dip2px(context, 25));
        paint.setTextAlign(Paint.Align.CENTER);

        float left = wrapWidth / 2 - defaultWidht / 2;
        float top = wrapHeight / 2 - defalutHeight / 2;
        float right = wrapWidth / 2 + defaultWidht / 2;
        float bottom = wrapHeight / 2 + defalutHeight / 2;
        rectF = new RectF(left, top, right, bottom);

        centerX = left + defaultWidht / 2;
        centerY = top + defalutHeight / 2;

        setOnClickListener(this);
        setOnTouchListener(this);
        //启动动画
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 360f);
//        animator.setDuration(2000);
//        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(25);
        canvas.drawArc(rectF, 0, progress, false, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText((int) Math.ceil(progress * 100 / 360) + "%", centerX, centerY - (paint.ascent() + paint.descent()) / 2, paint);
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 360f);
        animator.setDuration(2000);
        animator.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float progress = getProgress();
            setProgress(progress += 1.5);
            invalidate();
        }
        //true即为拦截 false不拦截
        return true;
    }

}
