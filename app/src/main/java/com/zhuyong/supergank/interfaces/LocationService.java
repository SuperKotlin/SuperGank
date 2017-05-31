package com.zhuyong.supergank.interfaces;

import com.zhuyong.supergank.model.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyong on 2017/5/12.
 */
public interface LocationService {
    @GET("telematics/v3/weather?")
    Observable<Weather> getWeather(
            @Query("location") String location,
            @Query("output") String output,
            @Query("ak") String ak);
}

