package com.wxx.androiddemo.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wxx.androiddemo.R;


/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.contentprovider.provider
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button insert, query, delete;
    ContentResolver resolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_provider);
        insert = findViewById(R.id.insert);
        query = findViewById(R.id.query);
        delete = findViewById(R.id.delete);

        insert.setOnClickListener(this);
        query.setOnClickListener(this);
        delete.setOnClickListener(this);

        resolver = getContentResolver();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                ContentValues values = new ContentValues();
                int lineNo = (int) (Math.random() * 1000);
                values.put(ProviderEntity.LINE_NO, String.valueOf(lineNo));
                values.put(ProviderEntity.LINE_NAME, "测试" + Math.random() * 10);
                resolver.insert(ProviderEntity.TABLE_1_URI, values);

                ContentValues table2Value = new ContentValues();
                int price = (int) (Math.random() * 10 + 1);
                table2Value.put(ProviderEntity.PRICE_LINE_NO, lineNo);
                table2Value.put(ProviderEntity.PRICE_PRICE, price);
                resolver.insert(ProviderEntity.TABLE_2_URI, table2Value);
                break;
            case R.id.query:
                Cursor tab1Cursor = resolver.query(ProviderEntity.TABLE_1_URI, null, null, null, null);
                if (tab1Cursor != null && tab1Cursor.moveToFirst()) {
                    do {
                        String line_no = tab1Cursor.getString(tab1Cursor.getColumnIndex(ProviderEntity.LINE_NO));
                        String line_name = tab1Cursor.getString(tab1Cursor.getColumnIndex(ProviderEntity.LINE_NAME));
                        long id = tab1Cursor.getLong(tab1Cursor.getColumnIndex(ProviderEntity.LINE_ID));

                        Log.e("MainActivity",
                                "onClick(MainActivity.java:63)line_no=" + line_no + ",line_name=" + line_name + ",id=" + id);
                    } while (tab1Cursor.moveToNext());

                    tab1Cursor.close();
                }

                Cursor tab2Cursor = resolver.query(ProviderEntity.TABLE_2_URI, null, null, null, null);
                if (tab2Cursor != null && tab2Cursor.moveToFirst()) {
                    do {
                        String price_line_no = tab2Cursor.getString(tab2Cursor.getColumnIndex(ProviderEntity.PRICE_LINE_NO));
                        int prices = tab2Cursor.getInt(tab2Cursor.getColumnIndex(ProviderEntity.PRICE_PRICE));
                        Log.e("MainActivity",
                                "onClick(MainActivity.java:63)price_line_no=" + price_line_no + ",prices=" + prices);
                    } while (tab2Cursor.moveToNext());

                    tab2Cursor.close();
                }

                break;
            default:
                //指定ID删除
                Uri uri = ContentUris.withAppendedId(ProviderEntity.TABLE_1_URI, 1);
                resolver.delete(uri, null, null);
                break;
        }
    }
}
