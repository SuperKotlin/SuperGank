package com.zhuyong.supergank.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.base.SuperGankApplication;
import com.zhuyong.supergank.constact.Constant;
import com.zhuyong.supergank.interfaces.GankService;
import com.zhuyong.supergank.model.GanHuo;
import com.zhuyong.supergank.utils.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 启动页
 * Created by zhuyong on 2017/5/12.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.image_welcome)
    ImageView mImageWelcome;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    private String mUrl = "";

    private boolean mIsDefaultSplashPic = false;//默认不适用默认图加载启动页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        full(true);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mIsDefaultSplashPic = dataSPF.getBoolean(Constant.IS_DEFAULT_SPLASH_PIC, false);
        initVersion();
        getData();
    }

    private void initVersion() {
        mTvVersion.setText("版本号：V " + SystemUtils.getVersion(mContext));
    }

    private void getData() {

        SuperGankApplication.getRetrofit(Constant.GANK_BASE_URL)
                .create(GankService.class)
                .getGanHuo("福利", 1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GanHuo>() {
                    @Override
                    public void onCompleted() {
                        animateImage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Picasso.with(SplashActivity.this)
                                .load(R.mipmap.img_default)
                                .into(mImageWelcome);
                        animateImage();
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        mUrl = ganHuo.getResults().get(0).getUrl();
                        if (mIsDefaultSplashPic) {
                            Picasso.with(SplashActivity.this)
                                    .load(R.mipmap.img_default)
                                    .into(mImageWelcome);
                        } else {
                            Picasso.with(SplashActivity.this)
                                    .load(mUrl.equals("") ? "null" : mUrl)
                                    .into(mImageWelcome);
                        }
                    }
                });
    }

    private void animateImage() {
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        mImageWelcome.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrl);
                openActivity(MainActivity.class, bundle);
                overridePendingTransition(0, 0);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
