package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

//选择支付方式
public class SelectPayTypeActivity extends BaseActivity {

    @InjectView(R.id.order_title)
    TextView orderTitle;
    @InjectView(R.id.order_price)
    TextView orderPrice;
    @InjectView(R.id.bank_pay)
    RadioButton bankPay;
    @InjectView(R.id.ali_pay)
    RadioButton aliPay;
    @InjectView(R.id.wechat_pay)
    RadioButton wechatPay;
    @InjectView(R.id.types)
    RadioGroup types;
    @InjectView(R.id.to_pay_bt)
    TextView toPayBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_type);
        ButterKnife.inject(this);
        bankPay.setChecked(true);
        //选择支付方式
        types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId){
                    case R.id.ali_pay:
                        break;
                    case R.id.bank_pay:
                        break;
                    case R.id.wechat_pay:
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("支付");
    }
}
