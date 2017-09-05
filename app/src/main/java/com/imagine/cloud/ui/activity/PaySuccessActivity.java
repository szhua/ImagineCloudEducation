package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.AddOrderResultBean;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PaySuccessActivity extends BaseActivity {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.pay_type)
    TextView payType;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.check_my_meeting)
    TextView checkMyMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.inject(this);

        /*   bundle.putSerializable("data",addOrderResultBean);
            bundle.putString("type","bank");*/

        Bundle bundle =getIntent().getExtras();
        String type =bundle.getString("type");
        if("bank".equals(type)){
            payType.setText("快捷支付");
        }else{
            payType.setText("微信支付");
        }
        AddOrderResultBean  addOrderResultBean = (AddOrderResultBean) bundle.getSerializable("data");
        if(addOrderResultBean!=null){
            price.setText("￥"+addOrderResultBean.getPrice());
        }
        checkMyMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(MyMeetingActivity.class,null);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("支付成功");
    }
}
