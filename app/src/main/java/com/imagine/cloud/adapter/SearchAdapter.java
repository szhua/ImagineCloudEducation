package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.SearchRecordBean;

import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * SearchAdapter
 * 搜索
 */

public class SearchAdapter extends BaseQuickAdapter<SearchRecordBean ,BaseViewHolder> {
    public SearchAdapter(@Nullable List<SearchRecordBean> data) {
        super(R.layout.item_history_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, SearchRecordBean item) {
             helper.setText(R.id.text,item.getSearchKey());
    }
}
