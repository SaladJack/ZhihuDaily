package com.example.administrator.zhihudaily.presenter.contract;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.BaseView;
import com.example.administrator.zhihudaily.base.BaseViewInterface;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */

public interface HomeContract {

    interface View extends BaseView {
        void showTopStories(List<LatestResult.TopStoriesEntity> topStoriesEntityList);
        void showStroies(List<StoriesEntity> storiesEntityList);
        void showBefore(List<StoriesEntity> BeforeStoriesEntityList);
        void setDate(String date);
        void showError();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchLatestResult();
        void fetchBeforeStories(String date);
    }




}
