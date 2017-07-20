package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.Constant;
import com.imagine.cloud.bean.AlipayBean;
import com.imagine.cloud.bean.WechatPayBean;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.mayubao.pay_library.AliPayReq2;
import io.github.mayubao.pay_library.PayAPI;
import io.github.mayubao.pay_library.WechatPayReq;

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
                        aliPay(new AlipayBean());
                        break;
                    case R.id.bank_pay:
                        break;
                    case R.id.wechat_pay:
                        weChatPay(new WechatPayBean());
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

    public void weChatPay(WechatPayBean payBean){
        //1.创建微信支付请求
        //这里没有金额设置，金额的信息已经包含在预支付码prepayid了。
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(SelectPayTypeActivity.this) //activity实例
                .setAppId(Constant.APP_ID_WECHAT) //微信支付AppID
                .setPartnerId(payBean.getPartnerid())//微信支付商户号
                .setPrepayId(payBean.getPrepayid())//预支付码
                .setPackageValue("Sign=WXPay")//"Sign=WXPay"
                .setNonceStr(payBean.getNoncestr())
                .setTimeStamp(payBean.getTimestamp())//时间戳
                .setSign(payBean.getSign())//签名
                .create();
        //2.发送微信支付请求
        PayAPI.getInstance().sendPayRequest(wechatPayReq);
    }

    //支付宝支付
    public void aliPay(AlipayBean alipayBean){
        //1.创建支付宝支付订单的信息
        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
                .setPartner(alipayBean.getPartner()) //商户PID || 签约合作者身份ID
                .setSeller(alipayBean.getSeller())  // 商户收款账号 || 签约卖家支付宝账号
                .setOutTradeNo(alipayBean.getOutTradeNo()) //设置唯一订单号
                .setSubject(alipayBean.getOrderSubject()) //设置订单标题
                .setBody(alipayBean.getOrderBody()) //设置订单内容
                .setPrice(alipayBean.getPrice()) //设置订单价格
                .setCallbackUrl(alipayBean.getCallbackUrl()) //设置回调链接
                .createOrderInfo(); //创建支付宝支付订单信息

        //2.签名  支付宝支付订单的信息 ===>>>  商户私钥签名之后的订单信息
        //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
        //getSignAliOrderInfoFromServer(rawAliOrderInfo);
        String signAliOrderInfo = "";
        //3.发送支付宝支付请求
        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(this)//Activity实例
                .setRawAliPayOrderInfo(rawAliOrderInfo)//支付宝支付订单信息
                .setSignedAliPayOrderInfo(signAliOrderInfo) //设置 商户私钥RSA加密后的支付宝支付订单信息
                .create()//
                .setOnAliPayListener(null);//
        PayAPI.getInstance().sendPayRequest(aliPayReq);
        //关于支付宝支付的回调
        aliPayReq.setOnAliPayListener(new AliPayReq2.OnAliPayListener(){

            @Override
            public void onPaySuccess(String resultInfo) {

            }
            @Override
            public void onPayFailure(String resultInfo) {

            }

            @Override
            public void onPayConfirmimg(String resultInfo) {

            }

            @Override
            public void onPayCheck(String status) {

            }
        });
    }


}
