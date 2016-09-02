package com.example.administrator.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.inter.MenuViewInterface;
import com.example.administrator.zhihudaily.presenter.MenuPresenter;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
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
    @BindView(R.id.tv_main)
    TextView tvMain;

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
}
