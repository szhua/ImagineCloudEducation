package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.MeetingOrderBean;
import com.imagine.cloud.net.Requst;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MyMeetingAdapter
 */

public class MyMeetingAdapter extends BaseQuickAdapter<MeetingOrderBean,BaseViewHolder> {
    public MyMeetingAdapter(@Nullable List<MeetingOrderBean> data) {
        super(R.layout.item_home_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MeetingOrderBean item) {
        //设置图片
        Picasso.with(mContext).load(Requst.BASE_IMG_URL).placeholder(R.drawable.loading_1_1).into((ImageView) helper.getView(R.id.image));
        //设置文字
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.content,item.getSubtitle())
                .setText(R.id.time,item.getCreate_time());
    }
}
