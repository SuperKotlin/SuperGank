package com.zhuyong.supergank.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.SuperKotlin.pictureviewer.ImagePagerActivity;
import com.SuperKotlin.pictureviewer.PictureConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.adapter.GankGirlAdapter;
import com.zhuyong.supergank.base.SuperGankApplication;
import com.zhuyong.supergank.constact.Constant;
import com.zhuyong.supergank.interfaces.GankService;
import com.zhuyong.supergank.model.GanHuo;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 福利图片
 * Created by zhuyong on 2017/5/13.
 */
public class GankGirlActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    private static final String TAG = "GankGirlActivity";
    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<GanHuo.Result> ganHuoList;
    private GankGirlAdapter meiZhiAdapter;

    private int page = 1;
    private Handler handler = new Handler();
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private boolean mSwitch = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        setTitle("福利");
        initView();
        findViewById(R.id.img_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitch) {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    mSwitch = false;
                } else {
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    mSwitch = true;
                }
            }
        });
    }

    private void initView() {
        ganHuoList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(mContext);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        meiZhiAdapter = new GankGirlAdapter(mContext);
        dealWithAdapter(meiZhiAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<GanHuo.Result> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout, this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                jumpActivity(adapter, position);
            }
        });
    }

    private void jumpActivity(RecyclerArrayAdapter<GanHuo.Result> adapter, int position) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < adapter.getAllData().size(); i++) {
            list.add(adapter.getAllData().get(i).getUrl());
        }
        PictureConfig config = new PictureConfig.Builder()
                .setListData((ArrayList<String>) list)    //图片数据List<String> list
                .setPosition(position)    //图片下标（从第position张图片开始浏览）
                .setDownloadPath("SuperGank")    //图片下载文件夹地址
                .needDownload(true)    //是否支持图片下载
                .setPlacrHolder(R.mipmap.img_default)    //占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                .build();
        ImagePagerActivity.startActivity(mContext, config);
    }

    private void getData(int count, int page) {
        SuperGankApplication.getRetrofit(Constant.GANK_BASE_URL)
                .create(GankService.class)
                .getGanHuo("福利", count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuo>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        ganHuoList = ganHuo.getResults();
                        meiZhiAdapter.addAll(ganHuoList);
                    }
                });

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(20, page);
                page = 2;
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(20, page);
                page++;
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
