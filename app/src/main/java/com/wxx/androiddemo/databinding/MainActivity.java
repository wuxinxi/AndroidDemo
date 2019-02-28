package com.wxx.androiddemo.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.User;

import java.util.Random;

/**
 * 作者: TangRen on 2019/2/24
 * 包名：com.wxx.androiddemo.databinding
 * 邮箱：996489865@qq.com
 * TODO:databinding简单使用
 */
public class MainActivity extends AppCompatActivity {
    User user = new User("test", "why");
    MainBindingBinding binding;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_binding);
        binding.setUser(user);
        binding.setPresenter(new Presenter());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setmListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onUserClick(int position, User user) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //添加动画
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup root = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(root);
                return true;
            }
        });
    }

    String[] languages = new String[]{"Android", "Java", "Html", "RN", "Python"};

    String[] urls = new String[]{
            "https://avatar.csdnimg.cn/2/4/7/1_wu996489865.jpg",
            "https://avatars3.githubusercontent.com/u/15843806?s=400&u=717f48864122fbee961992272ab18665f3969718&v=4",
            "https://error"
    };

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_1:
                    int i = (int) (Math.random() * 1000 + 1);
                    user.setName(String.valueOf(i));
                    user.setIsVisable(!user.isVisable().get());
                    break;
                case R.id.add:
                    boolean isVisable = new Random().nextBoolean();
                    String language = languages[new Random().nextInt(5)];
                    user = new User(isVisable, language, language + "是这个世界上最好用的语言!");
                    user.setHeadImgUrl(urls[new Random().nextInt(3)]);
                    mAdapter.add(user);
                    break;
                case R.id.remove:
                    mAdapter.remove();
                    break;
                case R.id.btn_2:
                    user.setContent("随机变化:" + System.currentTimeMillis());
                    break;
                default:

                    break;
            }

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.user.setSelection(s.length());
        }

        /**
         * CheckBox事件
         *
         * @param view      .
         * @param isChecked .
         */
        public void onCheckedChanged(View view, boolean isChecked) {
            binding.setShowTextView(isChecked);
        }
    }
}
