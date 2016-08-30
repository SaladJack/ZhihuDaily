package com.example.administrator.zhihudaily.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.inter.HomeViewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeViewInterface{

    private Unbinder unbinder;
    private List<StoriesEntity> storiesEntityList = new ArrayList<>();
    private List<LatestResult.TopStoriesEntity> topStoriesEntityList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater,container,savedInstanceState);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void showTopStories(List<LatestResult.TopStoriesEntity> topStoriesEntityList) {
        this.topStoriesEntityList = topStoriesEntityList;
    }

    @Override
    public void showStroies(List<StoriesEntity> storiesEntityList) {
        this.storiesEntityList = storiesEntityList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
