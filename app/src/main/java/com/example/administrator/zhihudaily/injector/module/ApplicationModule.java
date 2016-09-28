package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.injector.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/28.
 */
@Module
public class ApplicationModule {
    private DailyApplication mDailyApplication;

    public ApplicationModule(DailyApplication dailyApplication) {
        this.mDailyApplication = dailyApplication;
    }

    @Provides
    @PerApplication
    public DailyApplication provideDailyApplication(){
        return mDailyApplication;
    }

}
