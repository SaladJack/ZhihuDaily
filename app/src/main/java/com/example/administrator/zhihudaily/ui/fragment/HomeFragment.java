package com.example.administrator.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.inter.HomeViewInterface;
import com.example.administrator.zhihudaily.presenter.HomePresenter;
import com.example.administrator.zhihudaily.presenter.MenuPresenter;
import com.example.administrator.zhihudaily.ui.adapter.HomeAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeViewInterface {

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    private Unbinder unbinder;
    private List<StoriesEntity> storiesEntityList = new ArrayList<>();
    private List<LatestResult.TopStoriesEntity> topStoriesEntityList = new ArrayList<>();
    private LinearLayoutManager llm;
    private HomeAdapter homeAdapter;
    private HomePresenter homePresenter;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        Logger.d("onCreateView");
        return rootView;
    }

    @Override
    protected void initData() {
        homeAdapter = new HomeAdapter(topStoriesEntityList,storiesEntityList);
        homePresenter = new HomePresenter(this);
        homePresenter.fetchTopStoriesAndStories();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvNews.setLayoutManager(llm);
        rvNews.setAdapter(homeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_news_layout;
    }

    @Override
    public void showTopStories(List<LatestResult.TopStoriesEntity> topStoriesEntityList) {
        this.topStoriesEntityList.clear();
        this.topStoriesEntityList.addAll(topStoriesEntityList);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showStroies(List<StoriesEntity> storiesEntityList) {
        this.storiesEntityList.clear();
        this.storiesEntityList.addAll(storiesEntityList);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
