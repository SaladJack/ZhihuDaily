package com.example.administrator.zhihudaily.base;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.ui.fragment.MenuFragment;
import com.example.administrator.zhihudaily.utils.SharedPrefUtils;
import com.orhanobut.logger.Logger;


public abstract class AppActivity extends BaseActivity {

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
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments() || getSupportFragmentManager().getFragments().get(0) instanceof MenuFragment) {

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
        ActivityManager.getInstance().finishActivity(this);
    }

    protected abstract void refreshUI();
}
