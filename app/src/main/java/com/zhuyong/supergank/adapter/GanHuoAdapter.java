package com.zhuyong.supergank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.zhuyong.supergank.R;
import com.zhuyong.supergank.model.GanHuo;
import com.zhuyong.supergank.utils.TimeUtil;

/**
 * Created by zhuyong on 2017/5/12.
 */
public class GanHuoAdapter extends RecyclerArrayAdapter<GanHuo.Result> {
    Context context;
    public GanHuoAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }

    class GanHuoViewHolder extends BaseViewHolder<GanHuo.Result> {
        private TextView title;
        private TextView type;
        private TextView who;
        private TextView time;

        public GanHuoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_ganhuo);
            title = $(R.id.title);
            type = $(R.id.type);
            who = $(R.id.who);
            time = $(R.id.time);
        }

        @Override
        public void setData(GanHuo.Result data) {
            super.setData(data);
            //标题
            title.setText(data.getDesc());
            //干货类型
            type.setText(data.getType());
            //根据干货类型动态替换干货图标
            if (data.getType().equals("Android")){
                setIconDrawable(type, MaterialDesignIconic.Icon.gmi_android);
            }else if (data.getType().equals("iOS")){
                setIconDrawable(type, MaterialDesignIconic.Icon.gmi_apple);
            }else if (data.getType().equals("休息视频")){
                setIconDrawable(type, MaterialDesignIconic.Icon.gmi_collection_video);
            }else if (data.getType().equals("前端")){
                setIconDrawable(type, MaterialDesignIconic.Icon.gmi_language_javascript);
            }
            //干货贡献者
            who.setText(data.getWho());
            //干货提交时间
            time.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
        }

        private void setIconDrawable(TextView view, IIcon icon) {
            view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(context)
                            .icon(icon)
                            .color(Color.BLACK)
                            .sizeDp(12),
                    null, null, null);
        }
    }
}
