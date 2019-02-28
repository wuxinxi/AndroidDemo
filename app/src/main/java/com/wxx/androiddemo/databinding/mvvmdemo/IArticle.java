package com.wxx.androiddemo.databinding.mvvmdemo;

import com.wxx.androiddemo.bean.ArticleBean;

import java.util.List;

/**
 * 作者：Tangren on 2019-02-28
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public interface IArticle {
    void onSuccess(List<ArticleBean.DataBean.DatasBean> articleList);

    void onFail(int what,String errorMsg);
}
