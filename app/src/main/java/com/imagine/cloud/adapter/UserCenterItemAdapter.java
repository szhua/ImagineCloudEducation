package com.imagine.cloud.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.MineListItemBean;
import java.util.List;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * UserCenterItemAdapter
 */
public class UserCenterItemAdapter extends BaseQuickAdapter<MineListItemBean ,BaseViewHolder> {

    public UserCenterItemAdapter(@Nullable List<MineListItemBean> data) {
        super(R.layout.item_mine_layout, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MineListItemBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.right_text,item.getRightText())
                .setImageResource(R.id.icon,item.getImg());

    }

}
