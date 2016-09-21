package com.example.administrator.zhihudaily.ui.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.inter.HomeViewInterface;
import com.example.administrator.zhihudaily.presenter.HomePresenter;
import com.example.administrator.zhihudaily.ui.adapter.HomeAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeViewInterface, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;
    @BindView(R.id.home_fragment_ll)
    LinearLayout homeFragmentLl;

    private Unbinder unbinder;
    private List<StoriesEntity> storiesEntityList = new ArrayList<>();
    private List<LatestResult.TopStoriesEntity> topStoriesEntityList = new ArrayList<>();
    private LinearLayoutManager llm;
    private HomeAdapter homeAdapter;
    private HomePresenter homePresenter;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean isLoading = false;
    private String date;
    static HomeFragment homeFragment;
    public static HomeFragment getInstance() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        homeAdapter = new HomeAdapter(topStoriesEntityList, storiesEntityList);
        homePresenter = new HomePresenter(this);
        homePresenter.fetchLatestResult();
    }



    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvNews.setLayoutManager(llm);
        rvNews.setAdapter(homeAdapter);
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        Logger.d("date: " + date);
                        homePresenter.fetchBeforeStories(date);
                    }
                }
            }
        });
        sr.setOnRefreshListener(this);
        sr.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        isLoading = false;
    }

    @Override
    public void showBefore(List<StoriesEntity> BeforeStoriesEntityList) {
        this.storiesEntityList.addAll(BeforeStoriesEntityList);
        homeAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void showError() {
        Snackbar.make(homeFragmentLl, "SnackbarTest", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        homePresenter.fetchLatestResult();
        sr.setRefreshing(false);
    }

    @Override
    public void refreshUI() {
        TypedValue itemStoryTextColor = new TypedValue();
        TypedValue itemStoryBackground = new TypedValue();
        TypedValue windowBackground = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.item_story_text_color, itemStoryTextColor, true);
        theme.resolveAttribute(R.attr.item_story_background_color, itemStoryBackground, true);
        theme.resolveAttribute(R.attr.windowBackground, windowBackground, true);

        Resources resources = getResources();
        View window=((ViewGroup) getActivity().getWindow().getDecorView());
        window.setBackgroundColor(resources.getColor(windowBackground.resourceId));
        int childCount = rvNews.getChildCount();
        int firstVisible = llm.findFirstVisibleItemPosition();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            int viewType = homeAdapter.getItemViewType(childIndex + firstVisible);
            switch (viewType) {
                case HomeAdapter.TOP_STORIES:
                    break;
                case HomeAdapter.STORIES:
                    ViewGroup storyView = (ViewGroup) rvNews.getChildAt(childIndex);
                    storyView.setBackgroundColor(resources.getColor(itemStoryBackground.resourceId));
//                    storyView.findViewById(R.id.rl_main_item).setBackgroundColor(itemStoryBackground.resourceId);
//                    ((TextView)storyView.findViewById(R.id.tv_title)).setTextColor(itemStoryTextColor.resourceId);
                    break;
            }
        }
        homeAdapter.notifyDataSetChanged();
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
