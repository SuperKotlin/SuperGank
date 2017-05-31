package com.zhuyong.supergank.interfaces;

import com.zhuyong.supergank.model.GanHuo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhuyong on 2017/5/12.
 */
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page);
}

