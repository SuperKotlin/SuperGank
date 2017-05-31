package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.squareup.picasso.Picasso;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.model.GanHuo;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class GankGirlAdapter extends RecyclerArrayAdapter<GanHuo.Result> {

    public GankGirlAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent);
    }

    class MeiZhiViewHolder extends BaseViewHolder<GanHuo.Result> {
        private ImageView image;

        public MeiZhiViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_picture);
            image = $(R.id.image);
        }

        @Override
        public void setData(GanHuo.Result data) {
            super.setData(data);
            Picasso.with(getContext())
                    .load(data.getUrl()).placeholder(R.mipmap.img_default)
                    .error(R.mipmap.img_default).into(image);
        }
    }
}
