package com.example.administrator.zhihudaily.net;



import com.example.administrator.zhihudaily.model.BeforeResult;
import com.example.administrator.zhihudaily.model.DetailResult;
import com.example.administrator.zhihudaily.model.LatestResult;
import com.example.administrator.zhihudaily.model.MenuResult;
import com.example.administrator.zhihudaily.model.NewsResult;


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
