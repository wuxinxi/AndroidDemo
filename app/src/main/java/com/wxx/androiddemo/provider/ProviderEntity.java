package com.wxx.androiddemo.provider;

import android.net.Uri;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.contentprovider.provider
 * 邮箱：996489865@qq.com
 * TODO:数据库相关
 */
public final class ProviderEntity {

    public static final String DB_NAME = "bus.db";
    public static final int VERSION = 1;

    public static final String TABLE_1 = "line";
    public static final String LINE_ID = "_id";
    public static final String LINE_NAME = "line_name";
    public static final String LINE_NO = "line_no";

    public static final String TABLE_2 = "price";
    public static final String PRICE_ID = "_id";
    public static final String PRICE_PRICE = "prices";
    public static final String PRICE_LINE_NO = "line_no";

    //唯一授权标示
    public static final String AUTHORITY = "com.provider";

    public static final Uri TABLE_1_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_1);

    public static final Uri TABLE_2_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_2);

    //MIME
    //1.必须以vnd开头
    //2.如果内容URI以路径结尾，则后面接android.cursor.dir/,如果内容URI以id结尾，则后面跟android.cursor.item/
    //3.最后接vnd.<authority>.<path>
    public static final String TABLE_1_DIR_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_1;
    public static final String TABLE_1_ITEM_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_1;

    public static final String TABLE_2_DIR_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_2;
    public static final String TABLE_2_ITEM_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_2;


}
