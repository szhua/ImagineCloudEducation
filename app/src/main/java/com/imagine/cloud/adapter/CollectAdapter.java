package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.ui.fragment.HomeFragment;

import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CollectAdapter
 */

public class CollectAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {
    public CollectAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }




}
