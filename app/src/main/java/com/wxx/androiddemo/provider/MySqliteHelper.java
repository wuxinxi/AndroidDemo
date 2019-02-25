package com.wxx.androiddemo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.contentprovider.provider
 * 邮箱：996489865@qq.com
 * TODO:数据库操作
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_1 = "CREATE TABLE IF NOT EXISTS "
                + ProviderEntity.TABLE_1
                + " (" + ProviderEntity.LINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ProviderEntity.LINE_NAME + " TEXT ,"
                + ProviderEntity.LINE_NO + " TEXT )";

        String sql_2 = "CREATE TABLE IF NOT EXISTS "
                + ProviderEntity.TABLE_2
                + "( " + ProviderEntity.PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ProviderEntity.PRICE_PRICE + " INTEGER ,"
                + ProviderEntity.PRICE_LINE_NO + " TEXT )";
        db.execSQL(sql_1);
        db.execSQL(sql_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProviderEntity.TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + ProviderEntity.TABLE_2);
        onCreate(db);
    }
}
