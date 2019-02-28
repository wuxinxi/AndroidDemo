package com.wxx.androiddemo.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.ArticleBean;

import java.util.List;

/**
 * 作者：Tangren on 2019-02-27
 * 包名：com.wxx.androiddemo.databinding
 * 邮箱：996489865@qq.com
 * TODO:binding适配器
 */
public class MyBindingAdapter {
    @BindingAdapter({"app:imgUrl", "app:placeholder"})
    public static void loadImageFromIntNet(ImageView img, String url, Drawable placeholder) {
        Glide.with(img.getContext())
                .load(url)
                .placeholder(placeholder)
                .error(R.mipmap.error)
                .into(img);
    }

    @BindingAdapter({"app:adapter"})
    public static void setAdapter(RecyclerView recyclerView,List<ArticleBean.DataBean.DatasBean> mList) {

    }
}
