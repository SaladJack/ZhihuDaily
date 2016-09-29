package com.example.administrator.zhihudaily.presenter.contract;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.BaseView;
import com.example.administrator.zhihudaily.entity.StoriesEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */

public interface NewsContract {

    interface View extends BaseView {
        void showStories(List<StoriesEntity> storiesEntityList);
    }

    interface Presenter extends BasePresenter<View>{
        void fetchNewsResult(int id);
    }
}
