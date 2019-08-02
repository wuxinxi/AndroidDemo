package com.wxx.androiddemo.ftp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：Tangren on 2019-04-28
 * 包名：com.wxx.androiddemo.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class FTPMainActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button button;
    TextView info;
    EditText path, fileName, ip, port, user, psw;
    ExecutorService service;
    Switch model;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            info.append(msg.obj.toString() + "\n");
        }
    };

    @Override
    protected int layout() {
        return R.layout.activity_ftp;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        button = findViewById(R.id.download);
        info = findViewById(R.id.info);
        path = findViewById(R.id.path);
        fileName = findViewById(R.id.fileName);
        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        path = findViewById(R.id.path);
        user = findViewById(R.id.user);
        psw = findViewById(R.id.psw);
        model = findViewById(R.id.model);
        button.setOnClickListener(this);
        model.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initData() {
        service = Executors.newSingleThreadExecutor();
        ftpUser = new FTPUser();
    }


    FTPUser ftpUser;

    @Override
    public void onClick(View v) {
        ftpUser.setUser(
                path.getText().toString().trim(),
                fileName.getText().toString().trim(),
                ip.getText().toString().trim(),
                Integer.valueOf(port.getText().toString().trim()),
                user.getText().toString().trim(),
                psw.getText().toString().trim());
        info.append("开始下载\n");
        service.submit(new DownloadThread());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ftpUser.setActiveMode(isChecked);
    }

    class DownloadThread extends Thread {
        @Override
        public void run() {
            super.run();
            new FTP()
                    .builder(ftpUser.getIp())
                    .setPort(ftpUser.getPort())
                    .setLogin(ftpUser.getUser(), ftpUser.getPsw())
                    .setFileName(ftpUser.getFileName())
                    .setDeleteOld(true)
                    .setPath(Environment.getExternalStorageDirectory() + "/")
                    .setFTPPath(ftpUser.getPath())
                    .setModel(ftpUser.isActiveMode())
                    .setTag("apk")
                    .download(new OnCallBack() {
                        @Override
                        public void onSuccess(String savePath) {
                            Log.d("FTPMainActivity",
                                    "onSuccess(FTPMainActivity.java:43)" + savePath);
                            Message message = Message.obtain();
                            message.what = 0;
                            message.obj = "下载成功：" + savePath;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onProgress(int progress) {
                            System.out.println("progress = " + progress);
                            if (progress == 100) {
                                System.out.println("----------------------------------------FTPMainActivity.onProgress----------------------------------------");
                            }

                            Message message = Message.obtain();
                            message.what = 0;
                            message.obj = "下载进度：" + progress;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFail(String msg) {
                            Log.e("FTPMainActivity",
                                    "onFail(FTPMainActivity.java:55)" + msg);
                            Message message = Message.obtain();
                            message.what = 0;
                            message.obj = "下载失败：" + msg;
                            handler.sendMessage(message);
                        }
                    });
        }
    }
}
