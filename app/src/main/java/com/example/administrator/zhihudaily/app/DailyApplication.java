package com.example.administrator.zhihudaily.app;

import android.app.Application;
import android.content.Context;

import com.example.administrator.zhihudaily.injector.component.ApplicationComponent;
import com.example.administrator.zhihudaily.injector.component.DaggerApplicationComponent;
import com.example.administrator.zhihudaily.injector.module.ApplicationModule;
import com.example.administrator.zhihudaily.injector.module.NetWorkModule;


/**
 * Created by Administrator on 2016/9/5.
 */

public class DailyApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupInjector();
    }

    private void setupInjector(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netWorkModule(new NetWorkModule(this))
                .build();
    }



    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }
}
