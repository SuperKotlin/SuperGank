package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhuyong.supergank.R;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class GirlNameListAdapter extends RecyclerArrayAdapter<String> {
    private Context context;
    private int textColor ;

    public GirlNameListAdapter(Context context) {
        super(context);
        this.context = context;
        textColor=context.getResources().getColor(R.color.text_color);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }

    class GanHuoViewHolder extends BaseViewHolder<String> {
        private TextView tv_name;

        public GanHuoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_name);
            tv_name = $(R.id.tv_name);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            //标题
            tv_name.setText(data);
            tv_name.setTextColor(textColor);
        }
    }
}
