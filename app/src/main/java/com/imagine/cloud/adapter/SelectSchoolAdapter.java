package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.SchoolBean;

import java.util.List;

/**
 * Created by szhua on 2017/7/21/021.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * SelectSchoolAdapter
 * 选择学校的adapter;
 */
public class SelectSchoolAdapter extends BaseQuickAdapter<SchoolBean,BaseViewHolder> {


    public SelectSchoolAdapter(@Nullable List<SchoolBean> data) {
        super(R.layout.item_select_school,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolBean item) {
        helper.setText(R.id.name,item.getName());
    }
}
