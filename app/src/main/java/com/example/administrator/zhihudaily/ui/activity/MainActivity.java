package com.example.administrator.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.base.AppActivity;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected BaseFragment getFirstFragment() {
        return HomeFragment.getInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fl_content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View view) {

    }



}
