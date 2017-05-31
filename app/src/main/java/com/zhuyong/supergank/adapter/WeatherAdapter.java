package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.squareup.picasso.Picasso;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.model.Weather;
import com.zhuyong.supergank.utils.TimeUtil;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class WeatherAdapter extends RecyclerArrayAdapter<Weather.DataBean.ResultWeatherDataList> {
    Context context;

    public WeatherAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }

    class GanHuoViewHolder extends BaseViewHolder<Weather.DataBean.ResultWeatherDataList> {
        private TextView tv_date;
        private TextView tv_weather;
        private TextView tv_temperature;
        private ImageView img_icon;

        public GanHuoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_weather);
            tv_date = $(R.id.tv_date);
            tv_weather = $(R.id.tv_weather);
            img_icon = $(R.id.img_icon);
            tv_temperature = $(R.id.tv_temperature);
        }

        @Override
        public void setData(Weather.DataBean.ResultWeatherDataList data) {
            super.setData(data);
            if (data.getDate().length() >= 2) {
                tv_date.setText(data.getDate().substring(0, 2));
            }else {
                tv_date.setText(data.getDate());
            }
            tv_weather.setText(data.getWeather());
            if (TimeUtil.getIsDayHour()) {
                Picasso.with(context).load(data.getDayPictureUrl()).into(img_icon);
            } else {
                Picasso.with(context).load(data.getNightPictureUrl()).into(img_icon);
            }
            tv_temperature.setText(data.getTemperature());
        }
    }
}
