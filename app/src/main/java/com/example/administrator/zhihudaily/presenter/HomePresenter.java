package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.inter.HomeViewInterface;
import com.orhanobut.logger.Logger;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomePresenter extends BasePresenter{
    private HomeViewInterface mHomeView;

    public HomePresenter(HomeViewInterface mHomeView) {
        super();
        this.mHomeView = mHomeView;
    }

    public void fetchTopStoriesAndStories(){
        zhihuService.getLatestResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestResult -> {
                    mHomeView.showTopStories(latestResult.getTop_stories());
                    mHomeView.showStroies(latestResult.getStories());
                    Logger.d(latestResult.toString());
                });
    }
}
