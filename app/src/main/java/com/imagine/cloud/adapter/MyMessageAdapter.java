package com.imagine.cloud.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;

import java.util.List;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MyMessageAdapter
 * 我的信息adapter；
 */

public class MyMessageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public MyMessageAdapter(List<String> data) {
        super(R.layout.item_message_layout,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
