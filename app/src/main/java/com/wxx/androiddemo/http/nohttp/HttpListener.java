package com.wxx.androiddemo.http.nohttp;

import com.yanzhenjie.nohttp.rest.Response;

public interface HttpListener<T> {

    void success(int what, Response<T> response);

    void fail(int what, String e);
}
