package com.example.administrator.zhihudaily.net;



import com.example.administrator.zhihudaily.entity.BeforeResult;
import com.example.administrator.zhihudaily.entity.DetailResult;
import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.MenuResult;
import com.example.administrator.zhihudaily.entity.NewsResult;
import com.example.administrator.zhihudaily.entity.StoriesEntity;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/30.
 */

public interface ZhihuService {

    @GET("themes")
    Observable<MenuResult> getMenuResult();

    @GET("news/latest")
    Observable<LatestResult> getLatestResult();

    @GET("news/before/{date}")
    Observable<BeforeResult> getBeforeResult(@Path("date") String date);

    @GET("news/{id}")
    Observable<DetailResult> getDetailResult(@Path("id") int id);

    @GET("theme/{id}")
    Observable<NewsResult> getNewsResult(@Path("id") int id);
}
