package com.wxx.androiddemo.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.User;

/**
 * 作者: TangRen on 2019/2/26
 * 包名：com.wxx.androiddemo.databinding
 * 邮箱：996489865@qq.com
 * TODO:
 */
public class MainActivity extends AppCompatActivity {
    User user = new User("wuxinxi", "m123456");
    MainBindingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_binding);
        binding.setUser(user);
        binding.setPresenter(new Presenter());

    }

    public class Presenter {

        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }
    }
}
