package com.example.administrator.zhihudaily.base;

import com.example.administrator.zhihudaily.net.ZhihuRetrofit;
import com.example.administrator.zhihudaily.net.ZhihuService;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/8/30.
 */

public class BasePresenter {
    protected Retrofit retrofit;
    protected ZhihuService zhihuService;
    public BasePresenter() {
        retrofit = ZhihuRetrofit.getRetrofit();
        zhihuService = retrofit.create(ZhihuService.class);
    }
}
