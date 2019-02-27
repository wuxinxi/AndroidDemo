package com.wxx.androiddemo.databinding;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * 作者：Tangren on 2019-02-27
 * 包名：com.wxx.androiddemo.databinding
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T mBinding;

    public BindingViewHolder(@NonNull T itemView) {
        super(itemView.getRoot());
        mBinding = itemView;
    }

    public T getmBinding() {
        return mBinding;
    }
}
