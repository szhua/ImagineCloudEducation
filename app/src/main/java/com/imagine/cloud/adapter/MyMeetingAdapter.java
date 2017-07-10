package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;

import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MyMeetingAdapter
 */

public class MyMeetingAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {
    public MyMeetingAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
