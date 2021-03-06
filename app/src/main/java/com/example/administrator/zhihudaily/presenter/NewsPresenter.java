package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.RxPresenter;
import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.contract.NewsContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/2.
 */

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter{

    private ZhihuService zhihuService;

    public NewsPresenter(ZhihuService zhihuService){
        this.zhihuService = zhihuService;
    }

    @Override
    public void fetchNewsResult(int id){
        Subscription subscription = zhihuService.getNewsResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResult -> mView.showStories(newsResult.getStories()),
                        error -> {});
        addSubscrebe(subscription);
    }
}
