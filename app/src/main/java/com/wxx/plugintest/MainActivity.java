package com.wxx.plugintest;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 作者: TangRen on 2019/3/31
 * 包名：com.wxx.plugintest
 * 邮箱：996489865@qq.com
 * TODO:组件开发：动态部署、热修复
 * TODO:组件开发：动态部署、热修复
 */
public class MainActivity extends BaseActivity {
    TextView textView;

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = findViewById(R.id.text);
    }

    @Override
    protected void initData() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "pluginapk-debug.apk");
        if (!file.exists()) {
            try {
                InputStream stream = getAssets().open("pluginapk-debug.apk");
                int size = stream.available();
                byte[] buffer = new byte[size];
                int read = stream.read(buffer);
                stream.close();

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(buffer);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DexClassLoader classLoader = new DexClassLoader(file.getPath(), Environment.getExternalStorageDirectory()+"", null, null);
        try {

            Class classz = classLoader.loadClass("com.wxx.pluginapk.util.PluginUtil");
            Object object = classz.newInstance();
            Method method = classz.getDeclaredMethod("getPluginPackageName", String.class);
            Object invoke = method.invoke(object, "我是上层发来的消息!!!");

            textView.setText(invoke.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
