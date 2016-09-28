package com.example.administrator.zhihudaily.inter;

import com.example.administrator.zhihudaily.entity.StoriesEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */

public interface NewsViewInterface {
    void showStories(List<StoriesEntity> storiesEntityList);
}
