package com.example.administrator.zhihudaily.injector.component;

import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.NewsModule;
import com.example.administrator.zhihudaily.injector.scope.PerActivity;
import com.example.administrator.zhihudaily.ui.fragment.NewsFragment;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/29.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, NewsModule.class})
public interface NewsComponent {
    void inject(NewsFragment newsFragment);
}
