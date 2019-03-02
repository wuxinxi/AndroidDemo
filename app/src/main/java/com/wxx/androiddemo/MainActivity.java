package com.wxx.androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityGo();
            }
        }, 1000);
    }

    private void startActivityGo() {
        Intent intent = new Intent(MainActivity.this, com.wxx.androiddemo.memory.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
