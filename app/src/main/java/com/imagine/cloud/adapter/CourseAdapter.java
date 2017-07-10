package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;

import java.util.List;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseAdapter
 */

public class CourseAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {

    public CourseAdapter(@Nullable List<String> data) {
        super(R.layout.item_course_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
