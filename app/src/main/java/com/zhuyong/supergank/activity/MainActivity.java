package com.zhuyong.supergank.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.adapter.GirlNameListAdapter;
import com.zhuyong.supergank.adapter.MainAdapter;
import com.zhuyong.supergank.adapter.WeatherAdapter;
import com.zhuyong.supergank.base.SuperGankApplication;
import com.zhuyong.supergank.constact.Constant;
import com.zhuyong.supergank.interfaces.LocationService;
import com.zhuyong.supergank.interfaces.OnLocationResultListener;
import com.zhuyong.supergank.model.Weather;
import com.zhuyong.supergank.utils.BitmapUtils;
import com.zhuyong.supergank.utils.StringUtils;
import com.zhuyong.supergank.view.MyListView;
import com.zhuyong.supergank.view.ResideLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.ColorPicker;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页
 * Created by zhuyong on 2017/5/12.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnLocationResultListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.tv_error_information)
    TextView mTvErrorInformation;
    @BindView(R.id.llayout_error)
    LinearLayout mLlayoutError;
    @BindView(R.id.img_left_bg)
    ImageView mImgLeftBg;
    @BindView(R.id.rl_root)
    ResideLayout mRlRoot;
    @BindView(R.id.recycler_view_name)
    EasyRecyclerView easyRecyclerView;
    GirlNameListAdapter nameListAdapter;
    @BindView(R.id.img_head)
    CircleImageView mImgHead;
    @BindView(R.id.tv_about_me)
    TextView mTvAboutMe;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.tv_text_color)
    TextView mTvTextColor;
    @BindView(R.id.tv_default)
    TextView mTvDefault;
    @BindView(R.id.view_top)
    View mViewTop;
    @BindView(R.id.view_bottom)
    View mViewBottom;

    private String mUrl = "";
    private boolean mIsDefaultSplashPic = false;//默认不适用默认图加载启动页
    private Bitmap bitmap;
    private String mLocation = "";
    private EasyRecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private View mHeaderView;
    private View mFootView;
    private MainAdapter mMainAdapter;
    private TextView mTvAddress;
    private TextView mTvTemperature;
    private TextView mTvTemperatureDetail;
    private TextView mTvWind;
    private TextView mTvWeather;
    private TextView mTvTemperaturePeople;
    private ImageView mImgWeatherBg;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImgLeftBg.setImageBitmap(
                    BitmapUtils.fastblur(mContext, bitmap, 1f, 15f));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.setOnLocationResultListener(this);
        initViewName();
        initView();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                location();
            }
        }, 1000);
    }

    private void initView() {
        mUrl = getIntent().getStringExtra("mUrl");
        mIsDefaultSplashPic = dataSPF.getBoolean(Constant.IS_DEFAULT_SPLASH_PIC, false);
        mTvDefault.setVisibility(mIsDefaultSplashPic ? View.VISIBLE : View.GONE);

        Picasso.with(mContext).load(TextUtils.isEmpty(mUrl) ? "null" : mUrl).into(mImgHead);
        if (TextUtils.isEmpty(mUrl) || mIsDefaultSplashPic) {
            mImgLeftBg.setImageBitmap(
                    BitmapUtils.fastblur(mContext, BitmapFactory.decodeResource(mContext.getResources()
                            , R.mipmap.img_default), 1f, 15f));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap = StringUtils.GetLocalOrNetBitmap(mUrl);
                    Message message = new Message();
                    handler.sendMessage(message);
                }
            }).start();
        }
    }


    private void initViewName() {

        easyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        nameListAdapter = new GirlNameListAdapter(mContext);
        easyRecyclerView.setAdapterWithProgress(nameListAdapter);

        nameListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    openActivity(GankGirlActivity.class);
                } else if (position == 1) {
                    openActivity(HotGirlActivity.class);
                } else if (position >= 2 && position <= 7) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cid", Constant.mNameList[position]);
                    openActivity(TemptationGirlActivity.class, bundle);
                } else if (position >= 8 && position <= 11) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", Constant.mNameList[position]);
                    openActivity(GanHuoActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("gid", Constant.mNameList[position]);
                    openActivity(OtherGirlActivity.class, bundle);
                }
            }
        });
        nameListAdapter.addAll(Constant.mNameData);
        mTvTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(MainActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        mViewTop.setBackgroundColor(pickedColor);
                        mViewBottom.setBackgroundColor(pickedColor);
                        mTvTextColor.setTextColor(pickedColor);
                        mTvSetting.setTextColor(pickedColor);
                        mTvDefault.setTextColor(pickedColor);
                        mTvAboutMe.setTextColor(pickedColor);
                        nameListAdapter.setTextColor(pickedColor);
                        nameListAdapter.notifyDataSetChanged();
                    }
                });
                picker.show();
            }
        });
    }


    @OnClick({R.id.llayout_error, R.id.llayout_default_root, R.id.tv_about_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llayout_error:
                recyclerView.setVisibility(View.VISIBLE);
                mLlayoutError.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        location();
                    }
                }, 1000);
                break;
            case R.id.llayout_default_root:
                if (mIsDefaultSplashPic) {
                    mIsDefaultSplashPic = false;
                    mTvDefault.setVisibility(View.GONE);
                } else {
                    mIsDefaultSplashPic = true;
                    mTvDefault.setVisibility(View.VISIBLE);
                }
                dataSPF.edit().putBoolean(Constant.IS_DEFAULT_SPLASH_PIC, mIsDefaultSplashPic).commit();
                break;
            case R.id.tv_about_me:
                new MaterialDialog.Builder(this)
                        .title(R.string.about_title)
                        .content(R.string.about_me)
                        .positiveText(R.string.close)
                        .show();
                break;
        }
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
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    private void doWeather(final String mLocation) {
        SuperGankApplication.getRetrofit(Constant.WEATHER_BASE_URL)
                .create(LocationService.class)
                .getWeather(mLocation, "json", Constant.BAIDU_WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        recyclerView.setVisibility(View.GONE);
                        mLlayoutError.setVisibility(View.VISIBLE);
                        mTvErrorInformation.setText(R.string.error);
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (weather.getError().equals("0") && weather.getStatus().equals("success")) {
                            recyclerView.setVisibility(View.VISIBLE);
                            mLlayoutError.setVisibility(View.GONE);
                            List<Weather.DataBean.ResultIndexList> listindex = weather.getResults().get(0).getIndex();
                            List<Weather.DataBean.ResultWeatherDataList> listweather = weather.getResults().get(0).getWeather_data();
                            String date = listweather.get(0).getDate().replaceAll(" ", "");// "周六",
                            String mWeather = listweather.get(0).getWeather();//  "晴转阴",
                            String wind = listweather.get(0).getWind();// ": "微风",
                            String[] aa = date.split("：");
                            mTvAddress.setText(mLocation);
                            mTvTemperature.setText(aa[1].replace("℃)", "") + "°");
                            mTvTemperatureDetail.setText(weather.getDate() + " " + aa[0].replace("(实时", "").substring(0, 2)
                                    + "  pm2.5 " + weather.getResults().get(0).getPm25());
                            mTvWind.setText(wind);
                            mTvWeather.setText(mWeather);
                            mTvTemperaturePeople.setText(aa[1].replace("℃)", "") + "°");

                            weatherAdapter.addAll(listweather);
                            mMainAdapter.replace(listindex);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            mLlayoutError.setVisibility(View.VISIBLE);
                            mTvErrorInformation.setText(R.string.error);
                        }
                    }
                });
    }


    private void init() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        weatherAdapter = new WeatherAdapter(mContext);
        dealWithAdapter(weatherAdapter);

        recyclerView.setRefreshListener(this);
    }

    private void initHeaderView(View view) {
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        mTvTemperatureDetail = (TextView) view.findViewById(R.id.tv_temperature_detail);
        mTvWind = (TextView) view.findViewById(R.id.tv_wind);
        mTvWeather = (TextView) view.findViewById(R.id.tv_weather);
        mTvTemperaturePeople = (TextView) view.findViewById(R.id.tv_temperature_people);
        mImgWeatherBg = (ImageView) view.findViewById(R.id.img_weather_bg);
        mImgWeatherBg.setImageBitmap(
                BitmapUtils.fastblur(mContext, BitmapFactory.decodeResource(mContext.getResources()
                        , R.mipmap.main_bg), 1f, 15f));
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Weather.DataBean.ResultWeatherDataList> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setError(R.layout.error_layout);
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.view_header, null);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initHeaderView(mHeaderView);
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return mHeaderView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        mFootView = LayoutInflater.from(mContext).inflate(R.layout.view_foot, null);
        mFootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        MyListView listView = (MyListView) mFootView.findViewById(R.id.list_index);
        listView.setFocusable(false);
        mMainAdapter = new MainAdapter(mContext);
        listView.setAdapter(mMainAdapter);
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return mFootView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                weatherAdapter.clear();
                doWeather(mLocation);
            }
        }, 1000);
    }

    @Override
    public void onLocationSuccess(String adddress) {
        weatherAdapter.clear();
        mLocation = adddress;
        doWeather(mLocation);
    }

    @Override
    public void onError() {
        recyclerView.setVisibility(View.GONE);
        mLlayoutError.setVisibility(View.VISIBLE);
        mTvErrorInformation.setText(R.string.location_error);
    }


    private long mCurrentTime = 0;

    @Override
    public void onBackPressed() {
        if (mRlRoot.isOpen()) {
            mRlRoot.closePane();
        } else {
            if (System.currentTimeMillis() - mCurrentTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(mContext, R.string.exit, Toast.LENGTH_SHORT).show();
            }
            mCurrentTime = System.currentTimeMillis();
        }
    }

}