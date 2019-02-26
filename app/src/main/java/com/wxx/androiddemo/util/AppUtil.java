package com.wxx.androiddemo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
}
