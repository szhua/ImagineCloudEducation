package com.imagine.cloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.orhanobut.logger.Logger;
import com.runer.net.ResultUtil;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

	if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			 String result =bundle.getString(JPushInterface.EXTRA_EXTRA);
            //跳转界面；
			if(!TextUtils.isEmpty(result)){
//				try {
//				     JsonNode node =	ResultUtil.handleResult(result);
//
//					Logger.d(result);
//					 String id =node.findValue("id").asText();
//					 String type =node.findValue("type").asText();
//				//	 String messageId =node.findValue("msgId").asText();
//					 if(!TextUtils.isEmpty(id)){
//						Intent i =new Intent(context, ArticleActivity.class) ;
//						i.putExtra("id" ,id) ;
//						i.putExtra("type",type);
//						i.putExtra("isFromMessage",true);
//					//	intent.putExtra("msgId",messageId);
//						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//						context.startActivity(i);
//					}
//				} catch (Exception  e) {
//					e.printStackTrace();
//				}
			}
        }
	}
}
