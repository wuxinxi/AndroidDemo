package com.wxx.androiddemo.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.contentprovider.provider
 * 邮箱：996489865@qq.com
 * TODO:自定义内容提供者
 */
public class MyContentProvider extends ContentProvider {
    private MySqliteHelper dbHelper;
    private SQLiteDatabase db;

    private static UriMatcher matcher;
    public static final int TABLE_CODE_1_DIR = 0;
    public static final int TABLE_CODE_1_ITEM = 1;
    public static final int TABLE_CODE_2_DIR = 2;
    public static final int TABLE_CODE_2_ITEM = 3;

    static {
        //NO_MATCH：不匹配任何路径返回码
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //表示Uri：content://com.provider/line,全查询，返回码TABLE_CODE_1_DIR
        matcher.addURI(ProviderEntity.AUTHORITY, ProviderEntity.TABLE_1, TABLE_CODE_1_DIR);
        ////表示Uri：content://com.provider/line/id,单条查询（/#通配符根据ID查找）
        matcher.addURI(ProviderEntity.AUTHORITY, ProviderEntity.TABLE_1 + "/#", TABLE_CODE_1_ITEM);

        //表示Uri：content://com.provider/price,TABLE_CODE_2_DIR
        matcher.addURI(ProviderEntity.AUTHORITY, ProviderEntity.TABLE_2, TABLE_CODE_2_DIR);
        matcher.addURI(ProviderEntity.AUTHORITY, ProviderEntity.TABLE_2 + "/#", TABLE_CODE_2_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MySqliteHelper(getContext(), ProviderEntity.DB_NAME, null, ProviderEntity.VERSION);
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase();
        String tableName = getTableName(uri);
        String where;
        switch (matcher.match(uri)) {
            case TABLE_CODE_1_DIR:
            case TABLE_CODE_2_DIR:
                //全查询
                return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
            case TABLE_CODE_1_ITEM:
            case TABLE_CODE_2_ITEM:
                //单条查询
                long id = ContentUris.parseId(uri);
                where = "_id=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(tableName, projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("无效的Uri:" + uri.toString());
        }
    }


    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case TABLE_CODE_1_DIR:
                return ProviderEntity.TABLE_1_DIR_MIME;
            case TABLE_CODE_1_ITEM:
                return ProviderEntity.TABLE_1_ITEM_MIME;
            case TABLE_CODE_2_DIR:
                return ProviderEntity.TABLE_2_DIR_MIME;
            case TABLE_CODE_2_ITEM:
                return ProviderEntity.TABLE_2_DIR_MIME;
            default:
                throw new IllegalArgumentException("无效Uri：" + uri.toString());
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long insert = db.insert(tableName, null, values);
        ContentResolver contentResolver = getContext().getContentResolver();
        if (contentResolver != null) {
            contentResolver.notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, insert);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        switch (matcher.match(uri)) {
            case TABLE_CODE_1_DIR:
            case TABLE_CODE_2_DIR:
                return db.delete(tableName, selection, selectionArgs);
            case TABLE_CODE_1_ITEM:
            case TABLE_CODE_2_ITEM:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }
                return db.delete(tableName, where, selectionArgs);
            default:
                throw new IllegalArgumentException("无效的Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        switch (matcher.match(uri)) {
            case TABLE_CODE_1_DIR:
            case TABLE_CODE_2_DIR:
                return db.update(tableName, values, selection, selectionArgs);
            case TABLE_CODE_1_ITEM:
            case TABLE_CODE_2_ITEM:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }
                return db.update(tableName, values, where, selectionArgs);
            default:
                throw new IllegalArgumentException("无效的Uri:" + uri.toString());
        }
    }

    public String getTableName(Uri uri) {
        switch (matcher.match(uri)) {
            case TABLE_CODE_1_DIR:
            case TABLE_CODE_1_ITEM:
                return ProviderEntity.TABLE_1;
            case TABLE_CODE_2_DIR:
            case TABLE_CODE_2_ITEM:
                return ProviderEntity.TABLE_2;
            default:
                throw new IllegalArgumentException("无效的Uri：" + uri.toString());

        }
    }
}
