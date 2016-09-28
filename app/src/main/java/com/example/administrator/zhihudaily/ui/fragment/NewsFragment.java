package com.example.administrator.zhihudaily.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.inter.NewsViewInterface;
import com.example.administrator.zhihudaily.presenter.NewsPresenter;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
import com.example.administrator.zhihudaily.ui.adapter.HomeAdapter;
import com.example.administrator.zhihudaily.ui.adapter.NewsAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    protected void initData() {
        menu = (MenuResult.Menu) getArguments().getSerializable("menu");
        newsAdapter = new NewsAdapter(menu, storiesEntityList);
        newsPresenter = new NewsPresenter(this);
        newsPresenter.fetchNewsResult(menu.getId());
    }



    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        ((MainActivity)getActivity()).setTitle(menu.getName());
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
    public void onRefresh() {
        newsPresenter.fetchNewsResult(menu.getId());
        sr.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshUI() {
        TypedValue itemStoryTextColor = new TypedValue();
        TypedValue itemStoryBackground = new TypedValue();
        TypedValue itemStoryLlBackground = new TypedValue();
        TypedValue windowBackground = new TypedValue();

        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.item_story_text_color, itemStoryTextColor, true);
        theme.resolveAttribute(R.attr.item_story_background_color, itemStoryBackground, true);
        theme.resolveAttribute(R.attr.item_story_ll_background_color, itemStoryLlBackground, true);
        theme.resolveAttribute(R.attr.windowBackground, windowBackground, true);

        Resources resources = getResources();
        View window=((ViewGroup) getActivity().getWindow().getDecorView());
        window.setBackgroundColor(resources.getColor(windowBackground.resourceId));
        int childCount = rvNews.getChildCount();
        int firstVisible = llm.findFirstVisibleItemPosition();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            int viewType = newsAdapter.getItemViewType(childIndex + firstVisible);
            switch (viewType) {
                case HomeAdapter.TOP_STORIES:
                    break;
                case HomeAdapter.STORIES:
                    ViewGroup storyView = (ViewGroup) rvNews.getChildAt(childIndex);
                    storyView.findViewById(R.id.ll_main_news_item).setBackgroundResource(itemStoryLlBackground.resourceId);
                    storyView.findViewById(R.id.rl_main_item).setBackgroundResource(itemStoryBackground.resourceId);
                    ((TextView)storyView.findViewById(R.id.tv_title)).setTextColor(resources.getColor(itemStoryTextColor.resourceId));
                    break;
            }
        }
        invalidateCacheItem();
    }

    /**
     * 使RecyclerView缓存中在pool中的Item失效
     */
    private void invalidateCacheItem() {
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(rvNews), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = rvNews.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
