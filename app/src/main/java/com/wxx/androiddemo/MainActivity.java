package com.wxx.androiddemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

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

        test();
    }

    private void startActivityGo() {
        Intent intent = new Intent(MainActivity.this, com.wxx.androiddemo.memory.MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void test() {
        WeakReference<String> stringWeakReference = new WeakReference<String>(new String("Hello"));
        System.out.println("MainActivity.test:" + stringWeakReference.get());
        System.gc();
        System.out.println("MainActivity.test 回收后:" + stringWeakReference.get());

        //执行结果为
        //Hello
        //null

        SoftReference<String> stringSoftReference = new SoftReference<>(new String("Hello SoftReference"));
        System.out.println("MainActivity.test :" + stringSoftReference.get());
    }


    private Map<String, SoftReference<Bitmap>> imgCache = new HashMap<>();

    public void addBitmapToCache(String pathName) {
        //强引用的bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        //软引用
        SoftReference<Bitmap> softBitmap = new SoftReference<>(bitmap);
        imgCache.put(pathName, softBitmap);
    }

    public Bitmap getBitmap(String pathName) {
        //从缓存中取出软引用的Bitmap
        SoftReference<Bitmap> softBitmap = imgCache.get(pathName);
        //可能为null，因为内存不足时JVM会进行回收
        return softBitmap.get();
    }
}
