package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.NavigationPresenter;
import com.example.administrator.zhihudaily.presenter.contract.NavigationContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/29.
 */
@Module
public class NavigationModule {
    private NavigationContract.View mNavigationViewInterface;

    public NavigationModule(NavigationContract.View mNavigationViewInterface) {
        this.mNavigationViewInterface = mNavigationViewInterface;
    }


    @Provides
    public NavigationPresenter provideNavigationPresenter(ZhihuService zhihuService){
        return new NavigationPresenter(zhihuService);
    }

}
