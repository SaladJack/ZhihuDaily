package com.example.administrator.zhihudaily.presenter.contract;

import com.example.administrator.zhihudaily.base.BasePresenter;
import com.example.administrator.zhihudaily.base.BaseView;
import com.example.administrator.zhihudaily.entity.MenuResult;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */

public interface NavigationContract {

    /**
     * Created by Administrator on 2016/8/30.
     */

    interface View extends BaseView{
        void showMenuItems(List<MenuResult.Menu> menuResultList);
    }

    interface Presenter extends BasePresenter<View>{
        void fetchMenus();
    }
}
