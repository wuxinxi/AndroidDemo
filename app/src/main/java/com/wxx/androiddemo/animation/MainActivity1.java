package com.wxx.androiddemo.animation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

/**
 * 作者：Tangren on 2019-02-25
 * 包名：com.wxx.androiddemo.animation
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity1 extends BaseActivity implements View.OnClickListener {
    Button jump;
    View shareView;
    View shareView2;

    @Override
    protected int layout() {
        return R.layout.main_animation_1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        jump = findViewById(R.id.jump);
        shareView = findViewById(R.id.shareView);
        shareView2 = findViewById(R.id.shareView2);
        jump.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity2.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair fist = new Pair(shareView, ViewCompat.getTransitionName(shareView));
            Pair second = new Pair(shareView2, ViewCompat.getTransitionName(shareView2));
            ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,fist,second);
            startActivity(intent,compat.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
