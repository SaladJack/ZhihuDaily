package com.example.administrator.zhihudaily.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.inter.MenuViewInterface;
import com.example.administrator.zhihudaily.presenter.MenuPresenter;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
import com.example.administrator.zhihudaily.ui.adapter.HomeAdapter;
import com.example.administrator.zhihudaily.ui.adapter.MenuAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */

public class MenuFragment extends Fragment implements MenuViewInterface {

    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;


    private List<MenuResult.Menu> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private MenuPresenter menuPresenter;
    private LinearLayoutManager llm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        menuPresenter = new MenuPresenter(this);
        menuPresenter.fetchMenus();
    }

    private void initView() {
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.setLayoutManager(llm);
        menuAdapter = new MenuAdapter(menuList);
        rvMenu.setAdapter(menuAdapter);
        tvMain.setOnClickListener(v -> {
            BaseFragment fragment = new HomeFragment();
            ((MainActivity)getActivity()).replaceFragment(fragment);
        });
    }

    @Override
    public void showMenuItems(List<MenuResult.Menu> menuList) {
        this.menuList.clear();
        this.menuList.addAll(menuList);
        menuAdapter.notifyDataSetChanged();
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
        llMenu.setBackgroundResource(headerBackground.resourceId);
        tvMain.setBackgroundResource(navdrawerBackground.resourceId);

        tvMain.setTextColor(resources.getColor(navdrawerTextColor.resourceId));
        rvMenu.setBackgroundResource(navdrawerBackground.resourceId);
        int childCount = rvMenu.getChildCount();
        int firstVisible = llm.findFirstVisibleItemPosition();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup menuView = (ViewGroup) rvMenu.getChildAt(childIndex + firstVisible);
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
            declaredMethod.invoke(declaredField.get(rvMenu), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = rvMenu.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


