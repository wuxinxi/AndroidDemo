package com.wxx.androiddemo.http.nohttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * 作者: Tangren on 2017/7/3
 * 包名：com.commonlylib.http
 * 邮箱：996489865@qq.com
 * TODO:请求JSONObject
 */

public class JsonRequest extends RestRequest<JSONObject> {

    public JsonRequest(String url) {
        this(url, RequestMethod.POST);
    }

    public JsonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_CONTENT_TYPE_JSON);
    }

    @Override
    public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        return JSON.parseObject(result);
    }


    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }
}
