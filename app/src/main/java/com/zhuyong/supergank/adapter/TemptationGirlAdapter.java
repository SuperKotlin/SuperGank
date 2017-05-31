package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.squareup.picasso.Picasso;
import com.zhuyong.supergank.R;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class TemptationGirlAdapter extends RecyclerArrayAdapter<String> {

    public TemptationGirlAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent);
    }

    class MeiZhiViewHolder extends BaseViewHolder<String> {
        private ImageView image;

        public MeiZhiViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_picture);
            image = $(R.id.image);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            Picasso.with(getContext())
                    .load(data).placeholder(R.mipmap.img_default)
                    .error(R.mipmap.img_default).into(image);
        }
    }
}
