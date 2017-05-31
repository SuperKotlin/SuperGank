package com.zhuyong.supergank.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.constact.Constant;
import com.zhuyong.supergank.interfaces.OnLocationResultListener;
import com.zhuyong.supergank.utils.SharedPreferencesUtils;

/**
 * Created by zhuyong on 2017/5/12.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 全局上下文
     */
    public Context mContext;
    public SharedPreferences dataSPF;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private OnLocationResultListener onLocationResultListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dataSPF = SharedPreferencesUtils.getInstance().getSharedPreferences(Constant.SPF_DATA);
    }

    /**
     * 实现对状态栏的控制
     *
     * @param enable
     */
    public void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams mLp = getWindow().getAttributes();
            mLp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(mLp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams mAttr = getWindow().getAttributes();
            mAttr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(mAttr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void setTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.tv_title);
        textView.setText(title);
        findViewById(R.id.rlayout_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void openActivity(Class c) {
        startActivity(new Intent(mContext, c));
    }

    public void openActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(mContext, c);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplication());

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3600000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //声明和设置定位回调监听器
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    onLocationResultListener.onLocationSuccess(amapLocation.getCity() + amapLocation.getDistrict());
                } else {
                    onLocationResultListener.onError();
                }
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }

    public void setOnLocationResultListener(OnLocationResultListener onLocationResultListener) {
        this.onLocationResultListener = onLocationResultListener;
    }

}
