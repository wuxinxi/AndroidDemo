package com.wxx.androiddemo.view.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.view.practice.progress.CircleProgressBar;

public class MainActivity extends AppCompatActivity {
    CircleProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bar = findViewById(R.id.progressBar);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(bar, "progress", 0, 360f);
//        animator.setDuration(2000);
//        animator.start();

    }
}
