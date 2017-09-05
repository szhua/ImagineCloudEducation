package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.AddOrderResultBean;
import com.imagine.cloud.bean.AlipayBean;
import com.imagine.cloud.bean.HomeMessageEvent;
import com.imagine.cloud.bean.PayResultEventBean;
import com.imagine.cloud.bean.WechatPayBean;
import com.imagine.cloud.dao.PayDao;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.mayubao.pay_library.PayAPI;
import io.github.mayubao.pay_library.WechatPayReq;
//选择支付方式  ==进行支付界面
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

    public static final int WECHAT_PAY = 0;
    public static final int BANK_VISA_PAY = 1;
    @InjectView(R.id.order_time)
    TextView orderTime;

    private int payType;

    private PayDao payDao;
    private AddOrderResultBean addOrderResultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_type);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        addOrderResultBean = (AddOrderResultBean) bundle.get("data");
        if (addOrderResultBean != null) {
            orderPrice.setText("￥" + addOrderResultBean.getPrice());
            orderTitle.setText("会议名称: "+addOrderResultBean.getTitle());
            orderTime.setText(addOrderResultBean.getCreate_time());
        }
        wechatPay.setChecked(true);
        payDao = new PayDao(this, this);
        //选择支付方式
        types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.ali_pay:
                        break;
                    case R.id.bank_pay:
                        payType = BANK_VISA_PAY;
                        break;
                    case R.id.wechat_pay:
                        payType = WECHAT_PAY;
                        break;
                }
            }
        });
        toPayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payType == WECHAT_PAY) {
                    payDao.wechatPay(String.valueOf(addOrderResultBean.getId()));
                    showProgressWithMsg(true,"正在进行微信支付");
                } else if (payType == BANK_VISA_PAY) {
                    Bundle data =new Bundle() ;
                    data.putSerializable("data", addOrderResultBean);
                    transUi(BankPayActivity.class, data);
                    finish();
                }
            }
        });
        EventBus.getDefault().register(this);
    }
    //微信支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent( PayResultEventBean resultEventBean) {
        if(resultEventBean.getPayResuly()==1){
            UiUtil.showLongToast(this,"支付成功");
            Bundle bundle =new Bundle() ;
            bundle.putSerializable("data",addOrderResultBean);
            bundle.putString("type","wechat");
            transUi(PaySuccessActivity.class,bundle);
            finish();
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.WECHAT_PAY) {
            WechatPayBean wechatPayBean = payDao.getWechatPayBean();
            weChatPay(wechatPayBean);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("支付");
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void weChatPay(WechatPayBean payBean) {
        //1.创建微信支付请求
        //这里没有金额设置，金额的信息已经包含在预支付码prepayid了。
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(SelectPayTypeActivity.this) //activity实例
                .setAppId(payBean.getAppid()) //微信支付AppID
                .setPartnerId(payBean.getPartnerid())//微信支付商户号
                .setPrepayId(payBean.getPrepayid())//预支付码;
                .setPackageValue("Sign=WXPay")//"Sign=WXPay"
                .setNonceStr(payBean.getNoncestr())
                .setTimeStamp(payBean.getTimestamp())//时间戳
                .setSign(payBean.getSign())//签名
                .create();
        //2.发送微信支付请求
        PayAPI.getInstance().sendPayRequest(wechatPayReq);

    }


//    //支付宝支付
//    public void aliPay(AlipayBean alipayBean) {
//        //1.创建支付宝支付订单的信息
//        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
//                .setPartner(alipayBean.getPartner()) //商户PID || 签约合作者身份ID
//                .setSeller(alipayBean.getSeller())  // 商户收款账号 || 签约卖家支付宝账号
//                .setOutTradeNo(alipayBean.getOutTradeNo()) //设置唯一订单号
//                .setSubject(alipayBean.getOrderSubject()) //设置订单标题
//                .setBody(alipayBean.getOrderBody()) //设置订单内容
//                .setPrice(alipayBean.getPrice()) //设置订单价格
//                .setCallbackUrl(alipayBean.getCallbackUrl()) //设置回调链接
//                .createOrderInfo(); //创建支付宝支付订单信息
//
//        //2.签名  支付宝支付订单的信息 ===>>>  商户私钥签名之后的订单信息
//        //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
//        //getSignAliOrderInfoFromServer(rawAliOrderInfo);
//        String signAliOrderInfo = "";
//        //3.发送支付宝支付请求
//        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
//                .with(this)//Activity实例
//                .setRawAliPayOrderInfo(rawAliOrderInfo)//支付宝支付订单信息
//                .setSignedAliPayOrderInfo(signAliOrderInfo) //设置 商户私钥RSA加密后的支付宝支付订单信息
//                .create()//
//                .setOnAliPayListener(null);//
//        PayAPI.getInstance().sendPayRequest(aliPayReq);
//        //关于支付宝支付的回调
//        aliPayReq.setOnAliPayListener(new AliPayReq2.OnAliPayListener() {
//            @Override
//            public void onPaySuccess(String resultInfo) {
//            }
//
//            @Override
//            public void onPayFailure(String resultInfo) {
//            }
//
//            @Override
//            public void onPayConfirmimg(String resultInfo) {
//            }
//
//            @Override
//            public void onPayCheck(String status) {
//            }
//        });
//    }


}
