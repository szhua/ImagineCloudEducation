package com.imagine.cloud.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.bean.MessageBean;
import com.imagine.cloud.util.AppUtil;
import com.runer.liabary.util.UiUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MyMessageAdapter
 * 我的信息adapter；
 */
public class MyMessageAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder> {
    public MyMessageAdapter(List<MessageBean> data) {
        super(R.layout.item_message_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper,final MessageBean item) {
        helper.setText(R.id.msg_type,item.getMessage_type())
                .setText(R.id.time,item.getCreate_tiem())
                .setText(R.id.title,item.getTitle())
                .setText(R.id.theme,item.getSubtitle());

        helper.getView(R.id.item_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemDeleteClickListener!=null){
                    onItemDeleteClickListener.onItemClick(item);
                }
            }
        });
        helper.getView(R.id.delete_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemDeleteClickListener!=null){
                    onItemDeleteClickListener.onItemDelete(item);
                }
            }
        });
        TextView msg =helper.getView(R.id.msg_type);
        //是否已读
        if("0".equals(item.getStatus())){
            UiUtil.setTextRLeftImage(msg,R.drawable.message_red);
        }else{
            UiUtil.setTextRLeftImage(msg,R.drawable.msg_icon);
        }
    }
    private OnItemDeleteClickListener onItemDeleteClickListener ;
    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }
    public interface  OnItemDeleteClickListener{
        void onItemDelete(MessageBean item );
        void onItemClick(MessageBean item);
    }
}
