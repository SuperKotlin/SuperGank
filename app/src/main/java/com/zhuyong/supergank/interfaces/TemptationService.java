package com.zhuyong.supergank.interfaces;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyong on 2017/5/12.
 */
public interface TemptationService {
    @GET("show.htm?")
    Observable<String> getPic(
            @Query("cid") String cid,
            @Query("pager_offset") int pager_offset);
}

