package com.example.administrator.zhihudaily.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */

public class MenuResult{

    private List<Menu> others;

    public List<Menu> getOthers() {
        return others;
    }

    public void setOthers(List<Menu> others) {
        this.others = others;
    }

    public class Menu {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }
}
