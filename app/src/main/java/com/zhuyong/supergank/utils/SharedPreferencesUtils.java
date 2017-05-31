package com.zhuyong.supergank.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.zhuyong.supergank.base.SuperGankApplication;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SharedPreferencesUtils {

    private Map<String, SharedPreferences> spfMaps = new HashMap<String, SharedPreferences>();

    private static SharedPreferencesUtils spfUtils;

    private SharedPreferencesUtils() {

    }

    /**
     * 自定义单例类
     */
    public static SharedPreferencesUtils getInstance() {
        if (spfUtils == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (spfUtils == null) {
                    spfUtils = new SharedPreferencesUtils();
                }
            }
        }
        return spfUtils;
    }

    /**
     * 获取SharedPreferences对象
     *
     * @param spfName 文件名
     * @return SharedPreferences对象
     */
    public SharedPreferences getSharedPreferences(String spfName) {
        if (spfMaps.containsKey(spfName)) {
            return spfMaps.get(spfName);
        } else {
            Context mContext = SuperGankApplication.getContext();
            SharedPreferences spf = mContext.getSharedPreferences(spfName, Activity.MODE_PRIVATE);
            spfMaps.put(spfName, spf);
            return spf;
        }
    }
}
