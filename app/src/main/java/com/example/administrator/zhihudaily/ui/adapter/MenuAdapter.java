package com.example.administrator.zhihudaily.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.entity.MenuResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MenuResult.Menu> menuList;
    private View view;

    public MenuAdapter(List<MenuResult.Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MenuHolder)holder).menu_item_name.setText(menuList.get(position).getName());
    }

    public class MenuHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.menu_item_name)
        TextView menu_item_name;

        public MenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
