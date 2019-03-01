package com.wxx.androiddemo.databinding.mvvmdemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wxx.androiddemo.BR;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.ArticleBean;
import com.wxx.androiddemo.databinding.BindingViewHolder;
import com.wxx.androiddemo.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren on 2019-02-28
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
    public class ArticleAdapter extends RecyclerView.Adapter<BindingViewHolder> {
        private LayoutInflater inflater;
        private List<ArticleBean.DataBean.DatasBean> mList;
        private Context mContext;

        public ArticleAdapter(Context mContext) {
            mList = new ArrayList<>();
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        @NonNull
        @Override
        public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_article, viewGroup, false);
            return new BindingViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BindingViewHolder bindingViewHolder, int i) {
            ArticleBean.DataBean.DatasBean datasBean = mList.get(i);
            datasBean.setPublishTimeStr(AppUtil.longTime2StringTime(datasBean.getPublishTime()));
            bindingViewHolder.getmBinding().setVariable(BR.article, datasBean);
            bindingViewHolder.getmBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mList != null ? mList.size() : 0;
        }

        public void addAll(List<ArticleBean.DataBean.DatasBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void loadRefresh(List<ArticleBean.DataBean.DatasBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

    }
