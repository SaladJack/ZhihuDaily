package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.inter.MenuViewInterface;
import com.example.administrator.zhihudaily.inter.StoryDetailViewInterface;
import com.orhanobut.logger.Logger;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/31.
 */

public class StoryDetailPresenter extends BasePresenter {
    StoryDetailViewInterface mStoryDetailViewInterface;
    public StoryDetailPresenter(StoryDetailViewInterface mStoryDetailViewInterface) {
        super();
        this.mStoryDetailViewInterface = mStoryDetailViewInterface;
    }

    public void fetchDetailResult(int id){
        zhihuService.getDetailResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailResult -> {
                    mStoryDetailViewInterface.showTitle(detailResult.getTitle());
                    mStoryDetailViewInterface.showTitlePage(detailResult.getImage());
                    mStoryDetailViewInterface.showWebView(detailResult.getBody());
                },
                error -> mStoryDetailViewInterface.showError());
    }
}
