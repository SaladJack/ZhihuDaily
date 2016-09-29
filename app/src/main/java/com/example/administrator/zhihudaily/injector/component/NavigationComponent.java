package com.example.administrator.zhihudaily.injector.component;

import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.NavigationModule;
import com.example.administrator.zhihudaily.injector.scope.PerActivity;
import com.example.administrator.zhihudaily.ui.fragment.NavigationFragment;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/29.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, NavigationModule.class})
public interface NavigationComponent {
    void inject(NavigationFragment navigationNavigationFragment);
}
