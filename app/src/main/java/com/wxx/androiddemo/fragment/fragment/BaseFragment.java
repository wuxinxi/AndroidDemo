package com.wxx.androiddemo.fragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment.fragment
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;

    protected abstract int layout();

    protected abstract void initView(View rootView, Bundle savedInstanceState);

    //是否已经加载过数据
    protected boolean isLoadData;

    //view是否已经初始化完毕
    protected boolean isLoadView;

    //当前界面是否可见
    protected boolean isVisibleToUser;

    //懒加载数据
    protected abstract void lazyLoadData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(getClass().getName(),
                "onAttach(" + getClass().getName() + ".java:30)onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getName(),
                "onCreate(" + getClass().getName() + ".java:38)onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(layout(), container, false);
            initView(rootView, savedInstanceState);
        }
        //防止多次加载
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        Log.e(getClass().getName(),
                "onCreateView(" + getClass().getName() + ".java:54)onCreateView");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLoadView = true;
        prepareFetData();
        Log.e(getClass().getName(),
                "onActivityCreated(" + getClass().getName() + ".java:62)onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getName(),
                "onStart(" + getClass().getName() + ".java:69)onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getName(),
                "onResume(" + getClass().getName() + ".java:76)onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getName(),
                "onPause(" + getClass().getName() + ".java:83)onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getName(),
                "onStop(" + getClass().getName() + ".java:91)onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getClass().getName(),
                "onDestroyView(" + getClass().getName() + ".java:98)onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getName(),
                "onDestroy(" + getClass().getName() + ".java:105)onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getClass().getName(),
                "onDetach(" + getClass().getName() + ".java:106)onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(getClass().getName(),
                "onHiddenChanged(" + getClass().getName() + ".java:113)onHiddenChanged=" + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetData();
        }
        Log.e(getClass().getName(),
                "setUserVisibleHint(" + getClass().getName() + ".java:120)setUserVisibleHint:" + isVisibleToUser);
    }

    public void prepareFetData() {
        Log.d("BaseFragment",
            "prepareFetData(BaseFragment.java:148)isVisiableToUser="+isVisibleToUser+",isLoadView="+isLoadView+",isLoadData="+isLoadData);
        if (isVisibleToUser && isLoadView && !isLoadData) {
            lazyLoadData();
            isLoadData = true;
        }
    }
}
