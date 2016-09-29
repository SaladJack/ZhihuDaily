package com.example.administrator.zhihudaily.presenter;


import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.RxPresenter;
import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.contract.HomeContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter{

    private ZhihuService zhihuService;

    public HomePresenter(ZhihuService zhihuService) {
        this.zhihuService = zhihuService;
    }

    @Override
    public void fetchLatestResult(){
        Subscription subscription = zhihuService.getLatestResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestResult -> {
                    mView.setDate(latestResult.getDate());
                    mView.showTopStories(latestResult.getTop_stories());
                    mView.showStroies(latestResult.getStories());
                }, error -> {});
        addSubscrebe(subscription);
    }

    @Override
    public void fetchBeforeStories(String date){
        zhihuService.getBeforeResult(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beforeResult -> {
                    mView.showBefore(beforeResult.getStories());
                    mView.setDate(beforeResult.getDate());
                });
    }

}
