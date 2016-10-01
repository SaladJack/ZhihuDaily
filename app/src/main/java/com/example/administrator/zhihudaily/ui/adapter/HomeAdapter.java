package com.example.administrator.zhihudaily.ui.adapter;

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
import com.example.administrator.zhihudaily.model.LatestResult;
import com.example.administrator.zhihudaily.model.StoriesEntity;
import com.example.administrator.zhihudaily.ui.activity.MainActivity;
import com.example.administrator.zhihudaily.ui.activity.StoryDetailActivity;
import com.example.administrator.zhihudaily.ui.view.Slider;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private  List<LatestResult.TopStoriesEntity> topStoriesEntityList;
    private  List<StoriesEntity> storiesEntityList;
    public static final int TOP_STORIES = 0;
    public static final int STORIES = 1;
    private View view;

    public HomeAdapter(List<LatestResult.TopStoriesEntity> topStoriesEntityList, List<StoriesEntity> storiesEntityList) {
        this.topStoriesEntityList = topStoriesEntityList;
        this.storiesEntityList = storiesEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TOP_STORIES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider,parent,false);
                return new TopStoryHolder(view);
            case STORIES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_news_item,parent,false);
                return new StoryHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopStoryHolder){
            ((TopStoryHolder)holder).slider.setTopEntities(topStoriesEntityList);
        }else if (holder instanceof StoryHolder){
            ((StoryHolder)holder).tv_title.setText(storiesEntityList.get(position - 1).getTitle());
            ((StoryHolder)holder).fl_story_item.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), StoryDetailActivity.class);
                intent.putExtra("id",storiesEntityList.get(position - 1).getId());
                v.getContext().startActivity(intent);
                ((MainActivity)v.getContext()).overridePendingTransition(R.anim.slide_in_to_left_from_right, 0);
            });
            Glide.with(((StoryHolder)holder).iv_title.getContext())
                    .load(storiesEntityList.get(position - 1).getImages().get(0))
                    .into(((StoryHolder)holder).iv_title);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TOP_STORIES : STORIES;
    }

    @Override
    public int getItemCount() {
        return 1 + storiesEntityList.size();
    }

    public class TopStoryHolder extends RecyclerView.ViewHolder implements Slider.OnItemClickListener{
        @BindView(R.id.kanner)
        Slider slider;

        public TopStoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            slider.setOnItemClickListener(this);
        }

        @Override
        public void click(View v, LatestResult.TopStoriesEntity entity) {
            Intent intent = new Intent(v.getContext(), StoryDetailActivity.class);
            intent.putExtra("id",entity.getId());
            v.getContext().startActivity(intent);
            ((MainActivity)v.getContext()).overridePendingTransition(R.anim.slide_in_to_left_from_right, 0);
        }
    }

    public class StoryHolder extends RecyclerView.ViewHolder{

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
