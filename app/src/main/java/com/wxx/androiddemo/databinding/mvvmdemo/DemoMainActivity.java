package com.wxx.androiddemo.databinding.mvvmdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.wxx.androiddemo.BR;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.databinding.MainMvvmBinding;

/**
 * 作者：Tangren on 2019-02-28
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class DemoMainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private MainMvvmBinding binding;
    private ArticleAdapter mAdapter;
    private ArticleViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_mvvm);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.refresh.setOnRefreshListener(this);
        mAdapter = new ArticleAdapter(getApplicationContext());
        binding.recyclerView.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(decoration);

        viewModel = new ArticleViewModel();
        binding.setVariable(BR.articleViewModel, viewModel);
        binding.refresh.post(new Runnable() {
            @Override
            public void run() {
                viewModel.fetch(0, 0,RequestType.FIRST_LOAD);
            }
        });
    }

    @Override
    public void onRefresh() {
        viewModel.fetch(0, 0,RequestType.DOWN_REFRESH_LOAD);
    }
}
