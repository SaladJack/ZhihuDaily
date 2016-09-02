package com.example.administrator.zhihudaily.entity;

import java.io.Serializable;
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

    public class Menu implements Serializable {
        private String name;
        private int id;
        private String thumbnail;
        private String description;

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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
