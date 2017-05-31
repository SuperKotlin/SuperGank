package com.zhuyong.supergank.interfaces;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyong on 2017/5/12.
 */
public interface OtherService {
    @GET("current.htm?")
    Observable<String> getGid(
            @Query("gid") String gid,
            @Query("pager_offset") int pager_offset);
}

