package com.example.administrator.zhihudaily.presenter.contract;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.BaseView;

/**
 * Created by Administrator on 2016/9/29.
 */

public interface StoryDetailContract {

    interface View extends BaseView {
        void showTitle(String title);
        void showTitlePage(String url);
        void showWebView(String body);
        void showError();
    }

    interface Presenter extends BasePresenter<View>{
        void fetchDetailResult(int id);
    }
}
