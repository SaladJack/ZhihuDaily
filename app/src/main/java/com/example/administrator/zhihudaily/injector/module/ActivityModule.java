package com.example.administrator.zhihudaily.injector.module;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.zhihudaily.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/28.
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    public Context context() {
        return mActivity;
    }

}
