package com.wxx.androiddemo;

import android.support.test.runner.AndroidJUnit4;

import com.wxx.retrofit.GitHubService;
import com.wxx.retrofit.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        service.getService().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.err.println("ExampleInstrumentedTest.onFailure");
            }
        });

    }
}
