package com.wxx.pluginapk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ClassLoader classLoader = getClassLoader();
        Class loaderClass = BaseDexClassLoader.class;
        File apk = new File(Environment.getExternalStorageDirectory() + "/pluginapk.apk");
        InputStream is = null;
        FileOutputStream out = null;
        if (!apk.exists()) {
            try {
                is = getAssets().open("pluginapk-debug.apk");
                int size = is.available();
                byte[] buffer = new byte[size];
                int read = is.read(buffer);
                is.close();

                out = new FileOutputStream(apk);
                out.write(buffer);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
            try {

                Field pathListField = loaderClass.getDeclaredField("pathList");
                pathListField.setAccessible(true);
                Object pathListObject = pathListField.get(classLoader);
                Class pathListClass = pathListObject.getClass();
                Field dexElementField = pathListClass.getDeclaredField("dexElements");

                dexElementField.setAccessible(true);
                PathClassLoader newClassLoader = new PathClassLoader(apk.getPath(), null);

                Object newPathListObject = pathListField.get(newClassLoader);
                Object newDexElementsObject = dexElementField.get(newPathListObject);
                dexElementField.set(pathListObject, newDexElementsObject);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "权限权限,我要权限!!!", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }
}
