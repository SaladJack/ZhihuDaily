package com.example.administrator.zhihudaily.inter;

import com.example.administrator.zhihudaily.base.BaseViewInterface;

/**
 * Created by Administrator on 2016/8/31.
 */

public interface StoryDetailViewInterface extends BaseViewInterface {
    void showTitle(String title);
    void showTitlePage(String url);
    void showWebView(String body);
}
