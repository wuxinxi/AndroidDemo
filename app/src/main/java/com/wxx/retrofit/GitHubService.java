package com.wxx.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 作者: TangRen on 2019/3/21
 * 包名：com.wxx.retrofit
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public interface GitHubService {
    @GET("users/wuxinxi")
    Call<User> getService();
}
