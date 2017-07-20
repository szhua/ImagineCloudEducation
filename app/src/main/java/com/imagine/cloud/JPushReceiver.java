package com.imagine.cloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.bean.MessageDetailBean;
import com.imagine.cloud.bean.MsgEvent;
import com.imagine.cloud.ui.activity.MeetingDetailActivity;
import com.imagine.cloud.ui.activity.MessageActivity;
import com.imagine.cloud.ui.activity.MessageDetailActivity;
import com.imagine.cloud.ui.activity.ProjectDetailActivity;
import com.orhanobut.logger.Logger;
import com.runer.net.ResultUtil;

import org.greenrobot.eventbus.EventBus;

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
		try{
			//发送消息
			MsgEvent msgEvent =new MsgEvent() ;
			msgEvent.setHasMsg(true);
			EventBus.getDefault().post(msgEvent);
		}catch (Exception e){
			e.printStackTrace();
		}

	if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			 String result =bundle.getString(JPushInterface.EXTRA_EXTRA);
            //跳转界面；
			if(!TextUtils.isEmpty(result)){
				/*{"url":"","mp_id":null,"message":"文本消息","type":3,"id":154}*/
				try {

                     Logger.d(result);
				     JsonNode node =	ResultUtil.handleResult(result);
					 String id =node.findValue("id").asText();
					 String type =node.findValue("type").asText();
                     String mp_id ="" ;
                     if(node.findValue("mp_id")!=null) {
                         mp_id = node.findValue("mp_id").asText();
                     }

                     Intent action  =null;
                     Bundle data =null ;
					//0会议  1 项目 3文本消息
					switch (type){
						case "0":
							action =new Intent(context, MeetingDetailActivity.class) ;
                            data =new Bundle() ;
                            data.putString("id",mp_id);
							data.putString("msg_id",id);
							data.putString("type","msg");
							break;
						case "1":
                            action =new Intent(context, ProjectDetailActivity.class) ;
                            data =new Bundle();
							data.putString("msg_id",id);
							data.putString("type","msg");
                            data.putString("id",mp_id);
							break;
						case "3":
                            action =new Intent(context, MessageDetailActivity.class) ;
                            data =new Bundle();
                            data.putString("id",id);
							break;
					}

					    if(action!=null){
                        action.putExtras(data);
						action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(action);
					    }
				} catch (Exception  e) {
					e.printStackTrace();
				}
			}
        }
	}
}
