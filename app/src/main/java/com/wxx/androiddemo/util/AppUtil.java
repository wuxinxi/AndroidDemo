package com.wxx.androiddemo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：Tangren on 2019-02-26
 * 包名：com.wxx.androiddemo.util
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class AppUtil {
    /**
     * @param context .
     * @return 分辨率信息
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static String getUrl(int page) {
        return "http://www.wanandroid.com/article/list/" + page + "/json";
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("zh", "CN"));

    public static String longTime2StringTime(long publishTime) {
        long time = publishTime / 1000;
        return format.format(new Date(time));
    }
}
