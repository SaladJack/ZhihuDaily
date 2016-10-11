package com.example.administrator.zhihudaily.net;

import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.app.Constants;
import com.example.administrator.zhihudaily.utils.NetworkUtils;

import java.io.File;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/30.
 */

public class ZhihuRetrofit {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(DailyApplication context){
        if (retrofit == null) {
            synchronized (ZhihuRetrofit.class) {
                if (retrofit == null) {
                    File cacheFile = new File(context.getApplicationContext().getExternalCacheDir(),"ZhihuDaily");
                    Cache cache = new Cache(cacheFile,1024*1024*50);
                    Interceptor interceptor = chain -> {
                            Request request = chain.request();
                            if (!NetworkUtils.isConnected(context.getApplicationContext())) {
                                request = request.newBuilder()
                                        .cacheControl(CacheControl.FORCE_CACHE)
                                        .build();
                            }
                            Response response = chain.proceed(request);
                            if (NetworkUtils.isConnected(context.getApplicationContext())) {
                                int maxAge = 0;
                                //有网络时,设置缓存超时时间0个小时
                                response.newBuilder()
                                        .header("Cache-Control", "public, max-age=" + maxAge)
                                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                        .build();
                            } else {
                                // 无网络时，设置超时为4周
                                int maxStale = 60 * 60 * 24 * 7 * 4;
                                response.newBuilder()
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .removeHeader("Pragma")
                                        .build();
                            }
                            return response;
                    };


                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .cache(cache)
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return retrofit;
    }

}
