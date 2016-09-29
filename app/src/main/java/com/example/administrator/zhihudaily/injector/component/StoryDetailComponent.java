package com.example.administrator.zhihudaily.injector.component;

import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.StoryDetailModule;
import com.example.administrator.zhihudaily.injector.scope.PerActivity;
import com.example.administrator.zhihudaily.ui.activity.StoryDetailActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/29.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, StoryDetailModule.class})
public interface StoryDetailComponent {
    void inject(StoryDetailActivity storyDetailActivity);
}
