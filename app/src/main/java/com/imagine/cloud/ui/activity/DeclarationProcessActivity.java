package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeclarationProcessActivity extends BaseActivity {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_process);
        ButterKnife.inject(this);

        List<String> itemData =new ArrayList<>() ;
        itemData.add("课题遴选");
        itemData.add("发布招标公告");
        itemData.add("提交申报材料");
        itemData.add("初级审评");
        itemData.add("评审立项") ;
        itemData.add("中期检查");
        itemData.add("结项鉴定");
        itemData.add("专著出版");

        ProItemListAdapter proItemListAdapter =new ProItemListAdapter(itemData);
        proItemListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(proItemListAdapter);


    }

    private class ProItemListAdapter extends BaseQuickAdapter<String ,BaseViewHolder>{

        public ProItemListAdapter(@Nullable List<String> data) {
            super(R.layout.item_pro_list,data);
        }
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tag,item);

            if(helper.getAdapterPosition()==getData().size()-1){
                helper.setVisible(R.id.arrow,false);
            }else{
                helper.setVisible(R.id.arrow,true);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        setTitle("申报流程图");
    }
}
