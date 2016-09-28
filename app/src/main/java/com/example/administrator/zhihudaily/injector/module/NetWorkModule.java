package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.injector.scope.PerApplication;
import com.example.administrator.zhihudaily.net.ZhihuRetrofit;
import com.example.administrator.zhihudaily.net.ZhihuService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/9/28.
 */
@Module
public class NetWorkModule {
    private DailyApplication mDailyApplication;

    public NetWorkModule(DailyApplication mDailyApplication) {
        this.mDailyApplication = mDailyApplication;
    }

    @Provides
    @PerApplication
    public ZhihuService provideZhihuService(Retrofit retrofit){
        return retrofit.create(ZhihuService.class);
    }

    @Provides
    @PerApplication
    public Retrofit provideRetrofit(){
        return ZhihuRetrofit.getRetrofit();
    }
}
