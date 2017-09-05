package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/28/028.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CheckBoughtDao
 */
public class CheckBoughtDao extends BaseRequest {
    public CheckBoughtDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    }
    /*user_id	是	string	用户id
meeting_id	是	string	会议id*/
    public void checkBought(String meetingId ,String user_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",user_id) ;
            param.put("meeting_id",meetingId) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.CHECK_BOUGHT),param, RequestCode.CODE_6);
    }
}
