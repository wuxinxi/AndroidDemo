package com.wxx.androiddemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

/**
 * 作者: TangRen on 2019/2/24
 * 包名：com.wxx.androiddemo.widget
 * 邮箱：996489865@qq.com
 * TODO:实现swipeRefreshLayout的上拉加载更多
 */
public class SwipeRefreshView extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    public SwipeRefreshView(@NonNull Context context) {
        super(context);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
