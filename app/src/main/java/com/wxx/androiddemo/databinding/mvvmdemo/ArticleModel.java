package com.wxx.androiddemo.databinding.mvvmdemo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wxx.androiddemo.bean.ArticleBean;
import com.wxx.androiddemo.http.nohttp.CallServer;
import com.wxx.androiddemo.http.nohttp.HttpListener;
import com.wxx.androiddemo.http.nohttp.JsonRequest;
import com.wxx.androiddemo.util.AppUtil;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 作者：Tangren on 2019-02-28
 * 包名：com.wxx.androiddemo.databinding.mvvmdemo
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class ArticleModel {
    private IArticle article;
    private JsonRequest request;
    private int what;

    public void setArticle(IArticle article) {
        this.article = article;
    }

    public void fetch(int what, int page, final RequestType type) {
        this.what = what;
        request = new JsonRequest(AppUtil.getUrl(page), RequestMethod.GET);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        CallServer.getHttpclient().add(what, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                ArticleBean articleBean = new Gson().fromJson(response.get().toJSONString(), ArticleBean.class);
                if (article != null) {
                    article.onSuccess(type,articleBean);
                }
            }

            @Override
            public void fail(int what, String e) {
                if (article != null) {
                    article.onFail(what, e);
                }
            }
        });
    }

    public void cancel() {
        if (request != null) {
            CallServer.getHttpclient().cancelWhat(what);
        }
    }
}
