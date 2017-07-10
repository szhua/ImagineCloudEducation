package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.base.Constant;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.net.Requst;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.List;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingAdapter
 */

public class MeetingAdapter extends BaseQuickAdapter<MeetingBean,BaseViewHolder> {
    public MeetingAdapter(@Nullable List<MeetingBean> data) {
        super(R.layout.item_home_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MeetingBean item) {
        //设置图片
        Picasso.with(mContext).load(Requst.BASE_IMG_URL+item.getImg()).placeholder(R.drawable.test).into((ImageView) helper.getView(R.id.image));
        //设置文字
        helper.setText(R.id.title,item.getTitle())
              .setText(R.id.content,item.getSubtitle())
              .setText(R.id.time,item.getCreate_time());
    }
}
