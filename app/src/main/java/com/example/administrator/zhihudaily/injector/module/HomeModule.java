package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.HomePresenter;
import com.example.administrator.zhihudaily.presenter.contract.HomeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/28.
 */
@Module
public class HomeModule {
    private HomeContract.View mView;

    public HomeModule(HomeContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public HomePresenter provideHomePresenter(ZhihuService zhihuService){
        return new HomePresenter(zhihuService);
    }
}
