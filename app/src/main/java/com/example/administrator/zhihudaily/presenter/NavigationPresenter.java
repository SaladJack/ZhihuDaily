package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.RxPresenter;
import com.example.administrator.zhihudaily.net.ZhihuService;
import com.example.administrator.zhihudaily.presenter.contract.NavigationContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/30.
 */

public class NavigationPresenter extends RxPresenter<NavigationContract.View> implements NavigationContract.Presenter{

    private ZhihuService zhihuService;

    public NavigationPresenter(ZhihuService zhihuService) {
        this.zhihuService = zhihuService;
    }

    @Override
    public void fetchMenus(){
        Subscription subscription = zhihuService.getMenuResult().cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuResult -> mView.showMenuItems(menuResult.getOthers()),
                        error -> {});
        addSubscrebe(subscription);
    }

}
