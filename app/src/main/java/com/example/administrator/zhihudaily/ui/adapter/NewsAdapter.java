package com.example.administrator.zhihudaily.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
import com.example.administrator.zhihudaily.ui.activity.StoryDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/2.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MenuResult.Menu menu;
    private List<StoriesEntity> storiesEntityList;
    public final int TITLE = 0;
    public final int STORIES = 1;
    private View view;
    private Context mContext;

    public NewsAdapter(MenuResult.Menu menu,List<StoriesEntity> storiesEntityList) {
        this.menu = menu;
        this.storiesEntityList = storiesEntityList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TITLE : STORIES;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType){
            case TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_header,parent,false);
                return new TopHolder(view);
            case STORIES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_news_item,parent,false);
                return new StoryHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopHolder){
            ((TopHolder)holder).tv_title.setText(menu.getDescription());
            Glide.with(mContext)
                    .load(menu.getThumbnail())
                    .into(((TopHolder)holder).iv_title);
        }else if (holder instanceof StoryHolder){
                ((StoryHolder)holder).tv_title.setText(storiesEntityList.get(position - 1).getTitle());
                ((StoryHolder)holder).fl_story_item.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), StoryDetailActivity.class);
                intent.putExtra("id",storiesEntityList.get(position - 1).getId());
                v.getContext().startActivity(intent);
                ((MainActivity)v.getContext()).overridePendingTransition(R.anim.slide_in_to_left_from_right, 0);
            });
            if (storiesEntityList.get(position - 1).getImages() == null){
                ((StoryHolder)holder).iv_title.setVisibility(View.GONE);
            }else if (storiesEntityList.get(position - 1).getType() == 1){
                Glide.with(mContext)
                        .load(storiesEntityList.get(position - 1).getImages().get(0))
                        .into(((StoryHolder)holder).iv_title);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1 + storiesEntityList.size();
    }

    static class TopHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_title)
        ImageView iv_title;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public TopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class StoryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fl_main_news_item)
        FrameLayout fl_story_item;
        @BindView(R.id.tv_topic)
        TextView tv_topic;
        @BindView(R.id.iv_title)
        ImageView iv_title;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public StoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
