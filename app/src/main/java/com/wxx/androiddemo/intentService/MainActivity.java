package com.wxx.androiddemo.intentService;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren on 2019-02-22
 * 包名：com.wxx.androiddemo.intentService
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity implements OnBitmapCallBack {
    ImageView img_1, img_2, img_3, img_4;
    List<ImageView> list;

    @Override
    protected int layout() {
        return R.layout.main_intent_service;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        list = new ArrayList<>();
        list.add(img_1);
        list.add(img_2);
        list.add(img_3);
        list.add(img_4);
    }

    String[] urls = new String[]{
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2624181337,2994538201&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2908440987,1551648475&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550815429011&di=cb29335c37505679f66d652894bbad38&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011163591d496ca801216a3eb754da.jpg%402o.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1447328869,646418415&fm=26&gp=0.jpg"
    };

    @Override
    protected void initData() {
        Intent intent = new Intent(this, MyIntentService.class);
        for (int i = 0; i < urls.length; i++) {
            intent.putExtra("url", urls[i]);
            intent.putExtra("index", i);
            startService(intent);
        }
        MyIntentService.setCallBack(this);
    }

    @Override
    public void setBitmap(int index, Bitmap bitmap) {
        if (bitmap != null) {
            list.get(index).setImageBitmap(bitmap);
        }
    }
}
