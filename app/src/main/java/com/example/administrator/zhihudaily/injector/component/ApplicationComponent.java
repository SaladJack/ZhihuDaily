package com.example.administrator.zhihudaily.injector.component;

import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.injector.module.ApplicationModule;
import com.example.administrator.zhihudaily.injector.module.NetWorkModule;
import com.example.administrator.zhihudaily.injector.scope.PerApplication;
import com.example.administrator.zhihudaily.net.ZhihuService;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/28.
 */
@PerApplication
@Component(modules = {ApplicationModule.class, NetWorkModule.class})
public interface ApplicationComponent {
    DailyApplication dailyApplication();

    ZhihuService zhihuService();
}
