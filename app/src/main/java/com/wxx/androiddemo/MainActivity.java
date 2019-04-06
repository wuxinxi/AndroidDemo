package com.wxx.androiddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int i = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (i != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "给我权限呀!!", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            } else {
                startActivityGo();
            }
        } else {
            startActivityGo();
        }
    }


    private void startActivityGo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, com.wxx.androiddemo.greendao.MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivityGo();
        }
    }
}
