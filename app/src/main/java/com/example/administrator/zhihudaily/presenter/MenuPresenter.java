package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.inter.MenuViewInterface;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/30.
 */

public class MenuPresenter extends BasePresenter{

    MenuViewInterface mMenuView;
    public MenuPresenter(MenuViewInterface mMenuView) {
        super();
        this.mMenuView = mMenuView;
    }

    public void fetchMenus(){
        zhihuService.getMenuResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuResult -> mMenuView.showMenuItems(menuResult.getOthers()));
    }

}
