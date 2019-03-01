package com.wxx.androiddemo.databinding.mvvmdemo;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.wxx.androiddemo.bean.ArticleBean;

/**
 * 作者：Tangren on 2019-03-01
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class ArticleViewModel implements IArticle {
    public ObservableField<ArticleBean> items = new ObservableField<>();
    public ObservableBoolean refreshing = new ObservableBoolean(true);
    private ArticleModel model;

    public ArticleViewModel() {
        model = new ArticleModel();
        model.setArticle(this);
    }

    /**
     *
     * @param what 请求what
     * @param page 页数
     * @param type 请求类型
     */
    public void fetch(int what, int page,RequestType type) {
        refreshing.set(true);
        model.fetch(what, page,type);
    }

    @Override
    public void onSuccess(RequestType type, ArticleBean article) {
        article.setType(type);
        items.set(article);
        refreshing.set(false);

    }

    @Override
    public void onFail(int what, String errorMsg) {
        refreshing.set(false);
    }
}
