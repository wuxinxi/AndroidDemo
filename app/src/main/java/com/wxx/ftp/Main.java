package com.wxx.ftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren on 2019-08-02
 * 包名：com.wxx.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "D:\\temp\\BJLogger";
        List<String> list = new ArrayList<>();
        File file = new File(path);
        String[] list1 = file.list();
//        list.add("D:\\temp\\BJLogger\\Q8A1B1T219024279-2019-05-10_0.xml");
//        list.add("D:\\temp\\BJLogger\\Q8A1B1T219024279-2019-05-11_0.csv");
        for (String s : list1) {
            list.add(path + "\\" + s);
        }
        FTPManage.Builder builder = new FTPManage
                .Builder("139.199.158.253", "ftpuser", "QW!@123qwe", "app-debug.apk")
                .setMainModel(true)
                .setLocalPath("H:\\文档\\北京\\二维码")
                .setDeleteOld(false)
                .setListPath(list)
                .setServicePath("/wuxinxi/logger3");
//        builder.create().download(new OnFtpCallBack() {
//            @Override
//            public void onSuccess(String savePath) {
//                System.out.println("Main.onSuccess path=" + savePath);
//            }
//
//            @Override
//            public void onProgress(int progress) {
//                System.out.println("progress = " + progress);
//            }
//
//            @Override
//            public void onFail(String msg) {
//                System.out.println("Main.onFail msg=" + msg);
//            }
//        });
//

        builder.create().upLoadFolder(new OnFtpCallBack() {
            @Override
            public void onSuccess(String savePath) {
                System.out.println("Main.onSuccess " + savePath);
            }

            @Override
            public void onProgress(int progress) {
                System.out.println("Main.onProgress " + progress);
            }

            @Override
            public void onFail(String msg) {
                System.out.println("Main.onFail " + msg);
            }
        });

    }
}
