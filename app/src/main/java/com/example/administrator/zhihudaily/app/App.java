package com.example.administrator.zhihudaily.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/9/5.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
