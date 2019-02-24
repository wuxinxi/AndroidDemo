package com.wxx.androiddemo.listview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;
import com.wxx.androiddemo.bean.User;
import com.wxx.androiddemo.listview.adapter.MyArrayAdapter;
import com.wxx.androiddemo.listview.adapter.MyBaseAdapter;
import com.wxx.androiddemo.sql.MySqlHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：Tangren on 2019-02-22
 * 包名：com.wxx.androiddemo.listview
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout refresh;
    ListView listView;
    MyHandler handler;
    List<User> list = new ArrayList<>();
    MyArrayAdapter arrayAdapter;
    MyBaseAdapter baseAdapter;
    String[] datas = new String[]{"Android", "Python", "Java", "C++"};

    @Override
    protected int layout() {
        return R.layout.main_list_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        refresh = findViewById(R.id.refresh);
        listView = findViewById(R.id.listView);
        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        refresh.setOnRefreshListener(this);

        View footView=getLayoutInflater().inflate(R.layout.item_foot_view,null);
        listView.addFooterView(footView);

        //ListView设置分割线比较简单，api就提供了 android:divider、 android:dividerHeight
        //也可以在布局中中设置view
    }

    @Override
    protected void initData() {
        handler = new MyHandler(this);
//        useArrayAdapter();
//        userSimpleAdapter();
//        userSimpleCursorAdapter();
        userBaseAdapter();
    }

    private void userBaseAdapter() {
        baseAdapter = new MyBaseAdapter(getApplicationContext(), list);
        listView.setAdapter(baseAdapter);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        handler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
    }

    private SQLiteDatabase db;

    //SimpleCursorAdapter
    private void userSimpleCursorAdapter() {
        MySqlHelper helper = new MySqlHelper(getApplicationContext(), MySqlHelper.DB_NAME, null, MySqlHelper.VERSION);
//        db = helper.getWritableDatabase();
//        for (String data : datas) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("book_name", data);
//            db.insert(MySqlHelper.TABLE_NAME, null, contentValues);
//        }

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(MySqlHelper.TABLE_NAME, null, null, null, null, null, null);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1, cursor, new String[]{"book_name"}, new int[]{android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(cursorAdapter);

    }

    //SimpleAdapter
    private void userSimpleAdapter() {
        List<Map<String, ?>> list = new ArrayList<>();
        for (String data : datas) {
            Map<String, String> map = new HashMap<>();
            map.put("bookName", data);
            list.add(map);
        }

        //简单使用
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                list, android.R.layout.simple_list_item_1, new String[]{"bookName"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }

    //ArrayAdapter
    private void useArrayAdapter() {
        //1.直接使用ArrayAdapter：如果item比较单一比如一个TextView,就可以直接使用而不需要自定义,甚至layout都可以使用系统的
        //datas长度一旦确定不可扩展，不能再调用adapter.add方法否则会UnsupportedOperationException异常
        final ArrayAdapter arrayAdapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(arrayAdapter2);

        if (true) {
            return;
        }

        //2.使用自定义的ArrayAdapter
        //因为ArrayAdapter接受泛型，所以容易扩展
        arrayAdapter = new MyArrayAdapter(getApplicationContext(), list);
        listView.setAdapter(arrayAdapter);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.sendEmptyMessage(arrayAdapter != null ? 0 : 1);
            }
        }).start();
    }


    static class MyHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            if (activity != null) {
                User uer = new User();
                uer.setContent("我是标题:" + SystemClock.elapsedRealtime());
                uer.setAge((int) (Math.random() * 30 + 1));
                uer.setName("Name" + (int) (Math.random() * 10));
                activity.list.add(uer);
                if (msg.what == 0) {
                    activity.arrayAdapter.notifyDataSetChanged();
                } else if (msg.what == 1) {
                    activity.baseAdapter.notifyDataSetChanged();
                }

                if (activity.refresh.isRefreshing()) {
                    activity.refresh.setRefreshing(false);
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}
