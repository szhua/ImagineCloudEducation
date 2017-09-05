package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.OrderInfoBean;
import com.imagine.cloud.dao.PayDao;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RefundActivity extends BaseActivity {

    @InjectView(R.id.rerson)
    EditText rerson;
    @InjectView(R.id.refund_bt)
    TextView refundBt;

    private PayDao payDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        ButterKnife.inject(this);

        payDao =new PayDao(this,this);
        final String order_id =getIntent().getStringExtra("order_id");

        refundBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(rerson.getText().toString())){
                    payDao.refund(order_id,rerson.getText().toString());
                    showProgress(true);
                }else{
                    UiUtil.showLongToast(RefundActivity.this,"请填写退款原因");
                }
            }
        });
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.RFUND){
            UiUtil.showLongToast(this,"您的退款申请已提交审核");
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("申请退款");
    }
}
