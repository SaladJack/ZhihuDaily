package com.example.administrator.zhihudaily.inter;

import com.example.administrator.zhihudaily.base.BaseViewInterface;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */

public interface HomeViewInterface extends BaseViewInterface {
    void showTopStories(List<LatestResult.TopStoriesEntity> topStoriesEntityList);
    void showStroies(List<StoriesEntity> storiesEntityList);
    void showBefore(List<StoriesEntity> BeforeStoriesEntityList);
    void setDate(String date);
}
