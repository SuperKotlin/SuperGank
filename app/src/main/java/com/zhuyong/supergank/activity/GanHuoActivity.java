package com.zhuyong.supergank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.adapter.GanHuoAdapter;
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
 * 干货列表
 * Created by zhuyong on 2017/5/13.
 */
public class GanHuoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    private static final String TAG = "GanHuoActivity";
    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<GanHuo.Result> ganHuoList;
    private GanHuoAdapter ganHuoAdapter;

    private int page = 1;
    private String title = "";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        findViewById(R.id.img_switch).setVisibility(View.GONE);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        initView();
    }

    private void initView() {
        ganHuoList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ganHuoAdapter = new GanHuoAdapter(mContext);
        dealWithAdapter(ganHuoAdapter);

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
                Intent intent = new Intent(mContext, GanHuoDetailActivity.class);
                jumpActivity(intent, adapter, position);
            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<GanHuo.Result> adapter, int position) {
        intent.putExtra("desc", adapter.getItem(position).getDesc());
        intent.putExtra("url", adapter.getItem(position).getUrl());
        startActivity(intent);
    }

    private void getData(String title, int count, int page) {
        SuperGankApplication.getRetrofit(Constant.GANK_BASE_URL)
                .create(GankService.class)
                .getGanHuo(title, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        ganHuoList = ganHuo.getResults();
                        ganHuoAdapter.addAll(ganHuoList);
                    }
                });

    }


    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ganHuoAdapter.clear();
                getData(title, 20, 1);
                page = 2;
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(title, 20, page);
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
