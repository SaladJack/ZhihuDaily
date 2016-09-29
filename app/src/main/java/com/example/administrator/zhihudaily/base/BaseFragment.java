package com.example.administrator.zhihudaily.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{

    @Inject
    protected T mPresenter;
    protected BaseActivity mActivity;
    private Unbinder unbinder;

    protected abstract void initView(View view, Bundle savedInstanceState);
    protected abstract void initData();
    public abstract void refreshUI();

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment)
            getHoldingActivity().addFragment(fragment);
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView(view, savedInstanceState);
        return view;
    }

    protected abstract void initInject();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
        unbinder.unbind();
    }
}
