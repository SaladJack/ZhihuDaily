package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.NewsPresenter;
import com.example.administrator.zhihudaily.presenter.contract.NewsContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/29.
 */
@Module
public class NewsModule {
    NewsContract.View mView;

    public NewsModule(NewsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public NewsPresenter provideNewsPresenter(ZhihuService zhihuService){
        return new NewsPresenter(zhihuService);
    }
}
