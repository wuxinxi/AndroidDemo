package com.wxx.androiddemo.fragment.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wxx.androiddemo.R;


/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment.fragment
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class FragmentA extends BaseFragment {
    TextView textView;

    public static FragmentA newInstance(String content) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(content)) {
            bundle.putString("content", content);
        }
        FragmentA fragmentA = new FragmentA();
        fragmentA.setArguments(bundle);
        return fragmentA;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        textView = rootView.findViewById(R.id.content);
        textView.setText(this.getClass().getSimpleName());
    }

    @Override
    protected void lazyLoadData() {
        Log.d("FragmentA",
                "lazyLoadData(FragmentA.java:43)加载数据");
    }


}
