package com.example.administrator.zhihudaily.base;

import com.example.administrator.zhihudaily.net.ZhihuRetrofit;
import com.example.administrator.zhihudaily.net.ZhihuService;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/8/30.
 */

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();
}
