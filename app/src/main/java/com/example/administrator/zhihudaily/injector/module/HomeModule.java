package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.inter.HomeViewInterface;
import com.example.administrator.zhihudaily.presenter.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/28.
 */
@Module
public class HomeModule {
    private HomeViewInterface mHomeViewInterface;

    public HomeModule(HomeViewInterface mHomeViewInterface) {
        this.mHomeViewInterface = mHomeViewInterface;
    }

    @Provides
    public HomePresenter getHomePresenter(){
        return new HomePresenter(mHomeViewInterface);
    }
}
