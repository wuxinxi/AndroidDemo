package com.wxx.androiddemo.databinding.mvvmdemo;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wxx.androiddemo.bean.ArticleBean;

/**
 * 作者：Tangren on 2019-03-01
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class ArticleBindingAdapter {
    @BindingAdapter({"app:items"})
    public static void setItems(RecyclerView recyclerView, ArticleBean articleBean) {
        ArticleAdapter adapter = (ArticleAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            if (articleBean == null) {
                return;
            }
            if (articleBean.getType() == RequestType.FIRST_LOAD) {
                adapter.addAll(articleBean.getData().getDatas());
            } else if (articleBean.getType() == RequestType.DOWN_REFRESH_LOAD) {
                adapter.loadRefresh(articleBean.getData().getDatas());
            }
        }
    }

    @BindingAdapter({"app:refresh"})
    public static void setRefresh(SwipeRefreshLayout refresh, boolean refreshing) {
        refresh.setRefreshing(refreshing);
    }
}
