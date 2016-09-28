package com.example.administrator.zhihudaily.injector.component;

import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.HomeModule;
import com.example.administrator.zhihudaily.injector.scope.PerActivity;
import com.example.administrator.zhihudaily.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/28.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HomeModule.class})
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
