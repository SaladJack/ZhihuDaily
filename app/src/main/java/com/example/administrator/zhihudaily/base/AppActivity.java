package com.example.administrator.zhihudaily.base;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.ui.fragment.NavigationFragment;
import com.example.administrator.zhihudaily.utils.SharedPrefUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;


public abstract class AppActivity<T extends BasePresenter>  extends BaseActivity implements BaseView {
    @Inject
    protected T mPresenter;
    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isNightMode = SharedPrefUtils.isNightMode(this);
        if (isNightMode) setTheme(R.style.NightTheme);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (mPresenter != null) mPresenter.attachView(this);

        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments() || getSupportFragmentManager().getFragments().get(0) instanceof NavigationFragment) {

            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }

        ActivityManager.getInstance().addActivity(this);
    }


    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        ActivityManager.getInstance().finishActivity(this);
    }

    protected abstract void refreshUI();
}
