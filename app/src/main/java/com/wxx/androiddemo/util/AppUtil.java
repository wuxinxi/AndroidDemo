package com.wxx.androiddemo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    /**
     * 时间转换
     *
     * @param publishTime 时间戳
     * @return .
     */
    public static String longTime2StringTime(long publishTime) {
        return format.format(new Date(publishTime));
    }

    /**
     * @param context 上下文
     * @param pid     pid
     * @return 判断进程是否存在
     */
    public static boolean isProcessExit(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list;
        if (manager != null) {
            list = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
                if (runningAppProcessInfo.pid == pid) {
                    return true;
                }
            }
        }
        return false;
    }


}
