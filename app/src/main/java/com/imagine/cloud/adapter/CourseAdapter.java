package com.imagine.cloud.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.CourseBean;
import com.imagine.cloud.net.Requst;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseAdapter
 */

public class CourseAdapter extends BaseQuickAdapter<CourseBean,BaseViewHolder> {

    public CourseAdapter(@Nullable List<CourseBean> data) {
        super(R.layout.item_course_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {

        if(TextUtils.isEmpty(item.getImg())){
            helper.setVisible(R.id.image,false);
        }else{
            helper.setVisible(R.id.image,true);
        }
        Picasso.with(mContext).load(Requst.BASE_IMG_URL+item.getImg()).placeholder(R.drawable.loading_1_1).into((ImageView) helper.getView(R.id.image));
        helper.setText(R.id.play_time,item.getTime())
                .setText(R.id.title,item.getTitle())
                .setText(R.id.people_re_num,item.getNumb()) ;
        if(TextUtils.isEmpty(item.getAuthor())){
            helper .setText(R.id.author_name,"暂无作者");
        }else{
            helper.setText(R.id.author_name,item.getAuthor());
        }

    }
}
