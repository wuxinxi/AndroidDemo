package com.wxx.androiddemo.oss;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;
import com.wxx.androiddemo.http.nohttp.JsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.download.SimpleDownloadListener;
import com.yanzhenjie.nohttp.download.SyncDownloadExecutor;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者: TangRen on 2019/3/17
 * 包名：com.wxx.androiddemo.oss
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        int permission = ActivityCompat.checkSelfPermission(this,
                "android.permission.WRITE_EXTERNAL_STORAGE");

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            new MyThread().start();

        }

    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            String url = "http://47.93.255.115:8100/iotKeyInfos/Q8B52AT218239909";
            JsonRequest requestK = new JsonRequest(url, RequestMethod.GET);
            Response<JSONObject> execute = SyncRequestExecutor.INSTANCE.execute(requestK);
            JSONObject jsonData = execute.get();
            String bucketName = jsonData.getString("BucketName");
            String filePath = jsonData.getString("FilePath");
            String accessKeyId = jsonData.getString("AccessKeyId");
            String accessKeySecret = jsonData.getString("AccessKeySecret");
            String endpoint = "oss-cn-beijing.aliyuncs.com";
            String token = jsonData.getString("Token");
            String tokenExpireTime = jsonData.getString("TokenExpireTime");
            long currentTime = System.currentTimeMillis() / 1000;
            long ex = Long.valueOf(tokenExpireTime) - currentTime;

            Log.e("MyThread",
                    "run(MyThread.java:91)" + tokenExpireTime + ",ex=" + ex);
            OSSCredentialProvider provider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, token);
            OSS ossClient = new OSSClient(MainActivity.this, endpoint, provider);
            OSSLog.enableLog();
            String pathUrl = null;
            try {
                pathUrl = ossClient.presignConstrainedObjectURL(bucketName, filePath, 3600);
//                testDownLoad(pathUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String pathDir = Environment.getExternalStorageDirectory() + File.separator;
            DownloadRequest request1 = new DownloadRequest(pathUrl, RequestMethod.GET, pathDir, "key", true, true);
            try {
               request1.removeAllHeader();
                SyncDownloadExecutor.INSTANCE.execute(0, request1, new SimpleDownloadListener() {
                    @Override
                    public void onDownloadError(int what, Exception exception) {
                        super.onDownloadError(what, exception);
                        Log.e("STSActivity",
                                "onDownloadError(STSActivity.java:81)" + exception);
                    }

                    @Override
                    public void onFinish(int what, String filePath) {
                        super.onFinish(what, filePath);
                        Log.d("STSActivity",
                                "onFinish(STSActivity.java:88)下载成功");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void download(String downloadUrl) throws Exception {
        InputStream stream = getConnection(downloadUrl).getInputStream();
        FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + "test.jpg");
        int len = -1;
        byte[] buf = new byte[2048];

        while ((len = stream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }

        stream.close();
        outputStream.close();
    }


    public static void testDownLoad(String HTTP_URL) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            int contentLength = getConnection(HTTP_URL).getContentLength();
            System.out.println("文件的大小是:" + contentLength);
            if (contentLength > 32) {
                InputStream is = getConnection(HTTP_URL).getInputStream();
                bis = new BufferedInputStream(is);
                String path = Environment.getExternalStorageDirectory() + File.separator;
                FileOutputStream fos = new FileOutputStream(path + "美女.jpg");
                bos = new BufferedOutputStream(fos);
                int b = 0;
                byte[] byArr = new byte[1024];
                while ((b = bis.read(byArr)) != -1) {
                    bos.write(byArr, 0, b);
                }
                System.out.println("下载的文件的大小是----------------------------------------------:" + contentLength);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HttpURLConnection getConnection(String httpUrl) throws Exception {
        URL url = new URL(httpUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
//        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.connect();
        return connection;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new MyThread().start();
        }
    }
}
