package com.example.administrator.zhihudaily.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.model.MenuResult;
import com.example.administrator.zhihudaily.injector.component.DaggerNavigationComponent;
import com.example.administrator.zhihudaily.injector.component.NavigationComponent;
import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.NavigationModule;
import com.example.administrator.zhihudaily.presenter.NavigationPresenter;
import com.example.administrator.zhihudaily.presenter.contract.NavigationContract;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
import com.example.administrator.zhihudaily.ui.adapter.NavigationAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.ll_navigation)
    LinearLayout llNavigation;

    private List<MenuResult.Menu> menuList = new ArrayList<>();
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager llm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initInject() {
        NavigationComponent navigationComponent = DaggerNavigationComponent.builder()
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .navigationModule(new NavigationModule(this))
                .build();
        navigationComponent.inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.fetchMenus();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvNavigation.setLayoutManager(llm);
        navigationAdapter = new NavigationAdapter(menuList);
        rvNavigation.setAdapter(navigationAdapter);
        tvMain.setOnClickListener(v -> {
            BaseFragment fragment = new HomeFragment();
            ((MainActivity)getActivity()).replaceFragment(fragment);
        });
    }


    @Override
    public void showMenuItems(List<MenuResult.Menu> menuList) {
        this.menuList.clear();
        this.menuList.addAll(menuList);
        navigationAdapter.notifyDataSetChanged();
    }



    public void refreshUI() {
        TypedValue headerBackground = new TypedValue();
        TypedValue navdrawerBackground = new TypedValue();
        TypedValue navdrawerTextColor = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryDark, headerBackground, true);
        theme.resolveAttribute(R.attr.navdrawer_background, navdrawerBackground,true);
        theme.resolveAttribute(R.attr.navdrawer_text_color, navdrawerTextColor, true);
        Resources resources = getResources();
        llNavigation.setBackgroundResource(headerBackground.resourceId);
        tvMain.setBackgroundResource(navdrawerBackground.resourceId);

        tvMain.setTextColor(resources.getColor(navdrawerTextColor.resourceId));
        rvNavigation.setBackgroundResource(navdrawerBackground.resourceId);
        int childCount = rvNavigation.getChildCount();
        int firstVisible = llm.findFirstVisibleItemPosition();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup menuView = (ViewGroup) rvNavigation.getChildAt(childIndex + firstVisible);
            ((TextView)menuView.findViewById(R.id.menu_item_name)).setTextColor(resources.getColor(navdrawerTextColor.resourceId));
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
            declaredMethod.invoke(declaredField.get(rvNavigation), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = rvNavigation.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


