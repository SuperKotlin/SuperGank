package com.zhuyong.supergank.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zhuyong on 2017/5/12.
 */

public class SuperGankApplication extends Application {
    private static final String TAG = "SuperGankApplication";

    private static Context mContext;

    /**
     * baseurl必须以“/”结尾
     * //★这里最后面必须带“/”
     */
    private static final String GANHUO_API = "http://gank.io/";
    public static OkHttpClient client;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i(TAG, message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10000L, TimeUnit.SECONDS)
                .readTimeout(10000L, TimeUnit.SECONDS)
                .writeTimeout(10000L, TimeUnit.SECONDS)
                .build();

    }

    public static Retrofit getRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(SuperGankApplication.client)
                .addConverterFactory(ScalarsConverterFactory.create())//增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())//默认支持Gson解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 获取应用上下文对象
     */
    public static Context getContext() {
        return mContext;
    }

}
