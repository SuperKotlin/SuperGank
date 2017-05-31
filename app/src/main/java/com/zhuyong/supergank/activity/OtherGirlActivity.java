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
import com.zhuyong.supergank.adapter.TemptationGirlAdapter;
import com.zhuyong.supergank.base.SuperGankApplication;
import com.zhuyong.supergank.constact.Constant;
import com.zhuyong.supergank.interfaces.HotGirlService;
import com.zhuyong.supergank.interfaces.OtherService;
import com.zhuyong.supergank.model.DBParser;
import com.zhuyong.supergank.model.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 图片
 * Created by zhuyong on 2017/5/12.
 */
public class OtherGirlActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    private static final String TAG = "OtherGirlActivity";
    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private TemptationGirlAdapter adapter;
    private int page = 1;
    private Handler handler = new Handler();
    private String mGid = "";

    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private boolean mSwitch = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initData();
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
    private void initData() {
        mGid = getIntent().getStringExtra("gid");
        switch (mGid) {
            case Constant.URL_HAIXIUZU:
                setTitle(Constant.mNameData[12]);
                break;
            case Constant.URL_ZUIKAOPUDELIANAI:
                setTitle(Constant.mNameData[13]);
                break;
            case Constant.URL_DAXIONGMEI:
                setTitle(Constant.mNameData[14]);
                break;
            case Constant.URL_DACHANGTUI:
                setTitle(Constant.mNameData[15]);
                break;
            case Constant.URL_BEIJING:
                setTitle(Constant.mNameData[16]);
                break;
            case Constant.URL_HAOXIONGMEI:
                setTitle(Constant.mNameData[17]);
                break;
            case Constant.URL_KAOPUDELIANAI:
                setTitle(Constant.mNameData[18]);
                break;
            case Constant.URL_LUOLIDASHU:
                setTitle(Constant.mNameData[19]);
                break;
            case Constant.URL_NANPENGYOU:
                setTitle(Constant.mNameData[20]);
                break;
            case Constant.URL_RENXING:
                setTitle(Constant.mNameData[21]);
                break;
            case Constant.URL_BUZHNEGJING:
                setTitle(Constant.mNameData[22]);
                break;
            case Constant.URL_FACE:
                setTitle(Constant.mNameData[23]);
                break;
            case Constant.URL_LIANAI:
                setTitle(Constant.mNameData[24]);
                break;
            case Constant.URL_BAOZHAO:
                setTitle(Constant.mNameData[25]);
                break;
            case Constant.URL_XIAOZU:
                setTitle(Constant.mNameData[26]);
                break;
            case Constant.URL_BAOLOU:
                setTitle(Constant.mNameData[27]);
                break;
            case Constant.URL_SHUZU:
                setTitle(Constant.mNameData[28]);
                break;
            case Constant.URL_ROUNIU:
                setTitle(Constant.mNameData[29]);
                break;
        }
    }

    private void initView() {
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(mContext);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new TemptationGirlAdapter(mContext);
        dealWithAdapter2(adapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter2(final RecyclerArrayAdapter<String> adapter) {
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

    private void jumpActivity(RecyclerArrayAdapter<String> adapter, int position) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) adapter.getAllData());
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    private void getData(String mGid, int page) {
        SuperGankApplication.getRetrofit(Constant.PIC_BASE_URL)
                .create(OtherService.class)
                .getGid(mGid, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(String response) {
                        List<String> list = ImageHelper.saveImages(DBParser.parse(response));
                        adapter.addAll(list);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(mGid, page);
                page = 2;
            }
        }, 1000);
    }


    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(mGid, page);
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
