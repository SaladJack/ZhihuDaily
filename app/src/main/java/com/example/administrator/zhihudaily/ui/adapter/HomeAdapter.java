package com.example.administrator.zhihudaily.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;
import com.example.administrator.zhihudaily.ui.view.Kanner;
import com.orhanobut.logger.Logger;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private  List<LatestResult.TopStoriesEntity> topStoriesEntityList;
    private  List<StoriesEntity> storiesEntityList;
    public final int TOP_STORIES = 0;
    public final int STORIES = 1;
    private View view;

    public HomeAdapter(List<LatestResult.TopStoriesEntity> topStoriesEntityList, List<StoriesEntity> storiesEntityList) {
        this.topStoriesEntityList = topStoriesEntityList;
        this.storiesEntityList = storiesEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TOP_STORIES:
                Logger.d("Top");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kanner,parent,false);
                return new TopStoryHolder(view);
            case STORIES:
                Logger.d("Sub");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_news_item,parent,false);
                return new StroyHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopStoryHolder){
            ((TopStoryHolder)holder).kanner.setTopEntities(topStoriesEntityList);
        }else if (holder instanceof  StroyHolder){
            ((StroyHolder)holder).tv_title.setText(storiesEntityList.get(position - 1).getTitle());
            Glide.with(((StroyHolder)holder).iv_title.getContext())
                    .load(storiesEntityList.get(position - 1).getImages().get(0))
                    .into(((StroyHolder)holder).iv_title);
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

    public class TopStoryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.kanner)
        Kanner kanner;

        public TopStoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class StroyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_topic)
        TextView tv_topic;
        @BindView(R.id.iv_title)
        ImageView iv_title;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public StroyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
