package com.example.administrator.zhihudaily.inter;

import com.example.administrator.zhihudaily.entity.MenuResult;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */

public interface MenuViewInterface {
    void showMenuItems(List<MenuResult.Menu> menuResultList);
}
