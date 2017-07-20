package com.imagine.cloud.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.imagine.cloud.base.Constant;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI api;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.pay_result);
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
		  UiUtil.showLongToast(this,"支付成功!");
	  }else{
		  UiUtil.showLongToast(this,"支付失败");
		  //支付失败原因进行打印
		  Logger.d(resp.errStr);
		  Logger.d(resp.transaction);
	  }
}
	}
}