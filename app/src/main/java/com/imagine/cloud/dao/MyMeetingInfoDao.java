package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.OrderInfoBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/29/029.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MyMeetingInfoDao
 */

public class MyMeetingInfoDao extends BaseRequest {

    OrderInfoBean orderInfoBean ;


    public OrderInfoBean getOrderInfoBean() {
        return orderInfoBean;
    }

    public MyMeetingInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

           if(requestCode==RequestCode.CODE_0){
               orderInfoBean = JsonUtil.node2pojo(result,OrderInfoBean.class);
           }

    }

    public void getMyMeetingInfo(String id ,String order_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("id", id);
            param.put("order_id",order_id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.GET_ORDER_INFO),param, RequestCode.CODE_0);
    }
}
