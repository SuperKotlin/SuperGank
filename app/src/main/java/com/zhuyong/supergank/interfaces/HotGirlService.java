package com.zhuyong.supergank.interfaces;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyong on 2017/5/12.
 */
public interface HotGirlService {
    @GET("rank.htm?")
    Observable<String> getPicTwo(
            @Query("pager_offset") int pager_offset);
}

