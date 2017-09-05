package com.imagine.cloud.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.imagine.cloud.base.Constant;
import com.imagine.cloud.bean.PayResultEventBean;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/*微信支付回调
Activity*/
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d("startPay");
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WECHAT);
		api.handleIntent(getIntent(), this);
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}
	@Override
	public void onReq(BaseReq req) {
	}
	@Override
	public void onResp(BaseResp resp){
	if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
      if(resp.errCode==0){
		  UiUtil.showLongToast(getApplicationContext(),"支付成功!");
		  PayResultEventBean payResultEventBean =new PayResultEventBean() ;
		  payResultEventBean.setPayResuly(1);
		  EventBus.getDefault().post(payResultEventBean);
		  finish();
	  }else{
		  UiUtil.showLongToast(this,"支付失败");
		  //支付失败原因进行打印
		  Logger.d(resp.errStr);
		  Logger.d(resp.transaction);
		  finish();
	  }
}
	}
}