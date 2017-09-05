package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.bean.OrderInfoBean;
import com.imagine.cloud.dao.MyMeetingInfoDao;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutMeetingActivity extends BaseActivity {

    @InjectView(R.id.notice)
    TextView notice;
    @InjectView(R.id.calender)
    TextView calender;
    @InjectView(R.id.material)
    TextView material;
    @InjectView(R.id.invoice)
    TextView invoice;
    @InjectView(R.id.refund_bt)
    TextView refundBt;
    @InjectView(R.id.replenish)
    TextView replenish;

    private String id;
    private String order_id;
    private MyMeetingInfoDao myMeetingInfoDao;
    private OrderInfoBean orderInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_meeting);
        ButterKnife.inject(this);

        id = getStringExtras("id", "");
        order_id = getStringExtras("order_id", "");
        myMeetingInfoDao = new MyMeetingInfoDao(this, this);
        showProgress(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myMeetingInfoDao == null)
            myMeetingInfoDao = new MyMeetingInfoDao(this, this);
        myMeetingInfoDao.getMyMeetingInfo(id, order_id);
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            orderInfoBean = myMeetingInfoDao.getOrderInfoBean();
            //pay_status 3 进行退款中 1 退款成功 2 退款失败
            if (orderInfoBean != null) {
                if ("0".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("申请退款");
                } else if ("3".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("退款正在处理中");
                } else if ("2".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("退款失败，请联系客服查明原因");
                } else if ("1".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("退款成功");
                } else if ("4".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("退款审核中");
                } else if ("5".equals(orderInfoBean.getPay_status())) {
                    refundBt.setText("你的退款申请被驳回");
                }
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("会议相关");
    }

    @OnClick({R.id.notice, R.id.calender, R.id.material, R.id.invoice, R.id.refund_bt,R.id.replenish})
    public void onViewClicked(View view) {
        Bundle bundle = null;
        switch (view.getId()) {
            /*补充说明*/
            case R.id.replenish:
                bundle =new Bundle() ;
                bundle.putString(BaseWebAcitivity.WEB_TITLE, "补充说明");
                bundle.putString(BaseWebAcitivity.WEB_URL, orderInfoBean.getBuchong());
                transUi(BaseWebAcitivity.class, bundle);
                break;
            case R.id.notice:
                bundle = new Bundle();
                bundle.putString(BaseWebAcitivity.WEB_TITLE, "会议通知");
                bundle.putString(BaseWebAcitivity.WEB_URL, orderInfoBean.getTongzhi());
                transUi(BaseWebAcitivity.class, bundle);
                break;
            case R.id.calender:
                bundle = new Bundle();
                bundle.putString(BaseWebAcitivity.WEB_TITLE, "会议日程");
                bundle.putString(BaseWebAcitivity.WEB_URL, orderInfoBean.getRicheng());
                transUi(BaseWebAcitivity.class, bundle);
                break;
            case R.id.material:
                bundle = new Bundle();
                bundle.putString(BaseWebAcitivity.WEB_TITLE, "会议资料");
                bundle.putString(BaseWebAcitivity.WEB_URL, orderInfoBean.getZiliao());
                transUi(BaseWebAcitivity.class, bundle);
                break;
            case R.id.invoice:
                bundle = new Bundle();
                bundle.putString(BaseWebAcitivity.WEB_TITLE, "电子发票");
                bundle.putString(BaseWebAcitivity.WEB_URL, orderInfoBean.getFapiao());
                transUi(BaseWebAcitivity.class, bundle);
                break;
            case R.id.refund_bt:
                if (orderInfoBean != null) {
                    if ("0".equals(orderInfoBean.getPay_status())) {
                        Intent intent = new Intent(this, RefundActivity.class);
                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    } else if ("3".equals(orderInfoBean.getPay_status())) {
                        UiUtil.showLongToast(this, "退款正在处理中");
                    } else if ("2".equals(orderInfoBean.getPay_status())) {
                        UiUtil.showLongToast(this, "退款失败，请联系客服查明原因");
                    } else if ("1".equals(orderInfoBean.getPay_status())) {
                        UiUtil.showLongToast(this, "退款成功");
                    } else if ("4".equals(orderInfoBean.getPay_status())) {
                        UiUtil.showLongToast(this, "退款审核中");
                    } else if ("5".equals(orderInfoBean.getPay_status())) {
                        UiUtil.showLongToast(this, "你的退款申请被驳回");
                    }
                } else {
                    UiUtil.showLongToast(this, "出现错误");
                }
                break;
        }
    }


}
