//package com.wxx.androiddemo.databinding.mvvmdemo;
//
//import android.databinding.BaseObservable;
//
//import com.wxx.androiddemo.bean.ArticleBean;
//
//import java.util.List;
//
///**
// * 作者：Tangren on 2019-02-28
// * 包名：com.wxx.androiddemo.databinding.mvvmdemo
// * 邮箱：996489865@qq.com
// * TODO:一句话描述
// */
//public class ArticleViewModel2 extends BaseObservable implements IArticle {
//    ArticleAdapter mAdapter;
//
//    public ArticleViewModel2(ArticleAdapter mAdapter, int what, int page) {
//        this.mAdapter = mAdapter;
//        ArticleModel model = new ArticleModel();
//        model.setArticle(this);
//        model.fetch(what, page);
//    }
//
//    @Override
//    public void onSuccess(List<ArticleBean.DataBean.DatasBean> articleList) {
//        mAdapter.addAll(articleList);
//    }
//
//    @Override
//    public void onFail(int what, String errorMsg) {
//
//    }
//}
