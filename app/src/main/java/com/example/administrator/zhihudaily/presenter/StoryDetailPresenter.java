package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.RxPresenter;
import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.contract.StoryDetailContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/31.
 */

public class StoryDetailPresenter extends RxPresenter<StoryDetailContract.View> implements StoryDetailContract.Presenter{

    private ZhihuService zhihuService;

    public StoryDetailPresenter(ZhihuService zhihuService) {
        this.zhihuService = zhihuService;
    }

    @Override
    public void fetchDetailResult(int id){
        Subscription subscription = zhihuService.getDetailResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailResult -> {
                    mView.showTitle(detailResult.getTitle());
                    mView.showTitlePage(detailResult.getImage());
                    mView.showWebView(detailResult.getBody());
                },
                error -> {});
        addSubscrebe(subscription);
    }
}
