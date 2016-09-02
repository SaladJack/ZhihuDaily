package com.example.administrator.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.inter.NewsViewInterface;
import com.example.administrator.zhihudaily.presenter.NewsPresenter;
import com.example.administrator.zhihudaily.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/2.
 */

public class NewsFragment extends BaseFragment implements NewsViewInterface, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;
    private Unbinder unbinder;
    private List<StoriesEntity> storiesEntityList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private MenuResult.Menu menu;
    private LinearLayoutManager llm;
    private NewsPresenter newsPresenter;


    @Override
    public void onRefresh() {
        newsPresenter.fetchNewsResult(menu.getId());
        sr.setRefreshing(false);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvNews.setLayoutManager(llm);
        rvNews.setAdapter(newsAdapter);
        sr.setOnRefreshListener(this);
        sr.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void initData() {
        menu = (MenuResult.Menu) getArguments().getSerializable("menu");
        newsAdapter = new NewsAdapter(menu, storiesEntityList);
        newsPresenter = new NewsPresenter(this);
        newsPresenter.fetchNewsResult(menu.getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }


    @Override
    public void showStories(List<StoriesEntity> storiesEntityList) {
        this.storiesEntityList.clear();
        this.storiesEntityList.addAll(storiesEntityList);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
