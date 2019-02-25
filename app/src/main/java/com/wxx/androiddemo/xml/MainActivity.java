package com.wxx.androiddemo.xml;

import android.os.Bundle;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;
import com.wxx.androiddemo.xml.dom.DomParseThread;
import com.wxx.androiddemo.xml.pull.PullParseThread;

/**
 * 作者：Tangren on 2019-02-25
 * 包名：com.wxx.androiddemo.xml
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int layout() {
        return R.layout.main_xml;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        DomParseThread thread = new DomParseThread(getApplicationContext());
//        thread.start();

        new PullParseThread(getApplicationContext()).start();


    }

}
