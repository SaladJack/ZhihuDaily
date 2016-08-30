package com.example.administrator.zhihudaily.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.inter.MenuViewInterface;
import com.example.administrator.zhihudaily.presenter.MenuPresenter;
import com.example.administrator.zhihudaily.ui.adapter.MenuAdapter;

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

    private List<MenuResult.Menu> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private MenuPresenter menuPresenter;
    private LinearLayoutManager llm;

    @Nullable
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
    }

    @Override
    public void showMenuItems(List<MenuResult.Menu> menuList) {
        this.menuList.clear();
        this.menuList.addAll(menuList);
        menuAdapter.notifyDataSetChanged();
    }
}
