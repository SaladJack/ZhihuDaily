package com.example.administrator.zhihudaily.injector.module;

import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.StoryDetailPresenter;
import com.example.administrator.zhihudaily.presenter.contract.StoryDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/9/29.
 */
@Module
public class StoryDetailModule {
    private StoryDetailContract.View mView;

    public StoryDetailModule(StoryDetailContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public StoryDetailPresenter provideStoryDetailPresenter(ZhihuService zhihuService){
        return new StoryDetailPresenter(zhihuService);
    }
}
