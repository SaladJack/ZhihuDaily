package com.example.administrator.zhihudaily.net;



import com.example.administrator.zhihudaily.entity.LatestResult;
import com.example.administrator.zhihudaily.entity.MenuResult;



import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/30.
 */

public interface ZhihuService {

    @GET("themes")
    Observable<MenuResult> getMenuResult();

    @GET("news/latest")
    Observable<LatestResult> getLatestResult();
}
