package com.wxx.androiddemo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者: TangRen on 2019/2/24
 * 包名：com.wxx.androiddemo.sql
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MySqlHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Demo.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "table_1";


    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT ,book_name TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
