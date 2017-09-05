package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.ui.fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CollectAdapter
 */

public class CollectAdapter extends BaseQuickAdapter<MeetingBean,BaseViewHolder> {

    public CollectAdapter(@Nullable List<MeetingBean> data) {
        super(R.layout.item_collect_layout,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetingBean item) {

        if(TextUtils.isEmpty(item.getImg())){
            helper.setVisible(R.id.image,false);
        }else{
            helper.setVisible(R.id.image,true);
        }
        //设置图片
        Picasso.with(mContext).load(Requst.BASE_IMG_URL+item.getImg()).placeholder(R.drawable.loading_1_1).into((ImageView) helper.getView(R.id.image));
        //设置文字
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.content,item.getSubtitle())
                .setText(R.id.time,item.getCreate_time());
        View  deleteBt = helper.getView(R.id.delete_bt);

        helper.getView(R.id.item_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemDeleteClickListener!=null){
                    onItemDeleteClickListener.onItemClick(item);
                }
            }
        });
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(onItemDeleteClickListener!=null){
                   onItemDeleteClickListener.onItemDelete(item);
              }
            }
        });
    }

    private OnItemDeleteClickListener onItemDeleteClickListener ;
    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }
    public interface  OnItemDeleteClickListener{
        void onItemDelete(MeetingBean item );
        void onItemClick(MeetingBean item);
    }
}
