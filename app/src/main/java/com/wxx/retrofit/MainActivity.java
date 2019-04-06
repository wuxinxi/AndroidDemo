package com.wxx.retrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者: TangRen on 2019/3/24
 * 包名：com.wxx.retrofit
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    Gson gson = new Gson();

    @Override
    protected void initData() {
        Log.d("MainActivity",
                "initData(MainActivity.java:35)开始请求");
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        service.getService().enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.d("MainActivity",
                        "onResponse(MainActivity.java:45)" +response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.err.println("ExampleInstrumentedTest.onFailure");
                Log.e("MainActivity",
                        "onFailure(MainActivity.java:49)失败:" + t.getMessage());
            }
        });


        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url("")
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

            }
        });
    }
}
