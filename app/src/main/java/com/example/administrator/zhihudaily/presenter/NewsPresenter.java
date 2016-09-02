package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.inter.NewsViewInterface;
import com.orhanobut.logger.Logger;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/2.
 */

public class NewsPresenter extends BasePresenter {
    NewsViewInterface mNewsViewInterface;
    public NewsPresenter(NewsViewInterface mNewsViewInterface){
        super();
        this.mNewsViewInterface = mNewsViewInterface;
    }

    public void fetchNewsResult(int id){
        zhihuService.getNewsResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResult -> mNewsViewInterface.showStories(newsResult.getStories()));
    }
}
