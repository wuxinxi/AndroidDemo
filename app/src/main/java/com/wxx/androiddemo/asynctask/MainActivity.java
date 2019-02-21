package com.wxx.androiddemo.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者: TangRen on 2019/2/21
 * 包名：com.wxx.androiddemo.asynctask
 * 邮箱：996489865@qq.com
 * TODO:AsyncTask
 * 1.什么是AsyncTask：封装了线程池和Handler的异步任务框架
 * 2.怎么使用:3个泛型参数<Params, Progress, Result>,4个方法
 * 3.机制原理:
 * a.它本质上是一个静态的线程池，AsyncTask派生出的子类可以实现不同的异步任务，这些任务都是提交到静态的线程池中执行。
 * b.线程池中的工作线程执行doInBackground(mParams)方法执行异步的任务。
 * c.当任务状态改变后，工作线程向UI线程发送消息，AsyncTask内部的InternalHandler响应这些消息，并调用相关的回调函数。
 * 4.注意事项:内存泄漏、onDestroy cancel()调用、并行或串行(新版为顺序执行，如果想要并行则使用executeOnExecutor(Executor))
 */
public class MainActivity extends BaseActivity {
    ImageView img;
    MyAsyncTask asyncTask;

    @Override
    protected int layout() {
        return R.layout.main_async_task;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        img = findViewById(R.id.img);
    }

    @Override
    protected void initData() {
        String url = "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/58ee3d6d55fbb2fb3820d5714c4a20a44623dc4e.jpg";
        asyncTask = new MyAsyncTask(this);
        asyncTask.execute(url);
    }

    static class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private WeakReference<MainActivity> weakReference;

        public MyAsyncTask(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UI线程
            //开始执行任务前期
            //可做一些准备工作：progressBar设置可见
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //UI线程,进度

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                String urlStr = strings[0];
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //UI线程,doInBackground执行完毕回调的方法
            //做一些结束的工作：progressBar设置不可见
            //取出返回结果等
            MainActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                if (bitmap != null) {
                    mainActivity.img.setImageBitmap(bitmap);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }
}
