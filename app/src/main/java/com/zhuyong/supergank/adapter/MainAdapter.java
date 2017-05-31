package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuyong.supergank.R;
import com.zhuyong.supergank.model.Weather;
import com.zhuyong.supergank.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Weather.DataBean.ResultIndexList> mList = new ArrayList<>();

    private int[] color = {R.drawable.bg_weather_shape_sky_blue, R.drawable.bg_weather_shape_blue
            , R.drawable.bg_weather_shape_yellow, R.drawable.bg_weather_shape_green, R.drawable.bg_weather_shape_pink};

    public MainAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void append(Weather.DataBean.ResultIndexList itemDataType) {
        if (itemDataType != null) {
            mList.add(itemDataType);
            notifyDataSetChanged();
        }
    }

    public void replace(List<Weather.DataBean.ResultIndexList> itemDataTypes) {
        mList.clear();
        if (itemDataTypes.size() > 0) {
            mList.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_index, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Weather.DataBean.ResultIndexList itemData = mList.get(position);
        holder.mTvTitle.setText(itemData.getTitle() + ":" + itemData.getZs());
        holder.mTvContext.setText(itemData.getTipt() + ":" + itemData.getDes());
        holder.mLlayoutBgRoot.setBackgroundResource(color[position % 5]);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_context)
        TextView mTvContext;
        @BindView(R.id.llayout_root)
        LinearLayout mLlayoutBgRoot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}