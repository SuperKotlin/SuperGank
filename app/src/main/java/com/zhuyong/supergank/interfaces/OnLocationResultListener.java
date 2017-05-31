package com.zhuyong.supergank.interfaces;

/**
 * Created by zhuyong on 2017/5/18.
 * 定位回调
 */
public interface OnLocationResultListener {
    public void onLocationSuccess(String address);

    public void onError();
}
