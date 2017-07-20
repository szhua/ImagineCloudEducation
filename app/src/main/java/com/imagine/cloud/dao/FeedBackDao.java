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
 * Created by szhua on 2017/7/12/012.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FeedBackDao
 */

public class FeedBackDao extends BaseRequest {
    public FeedBackDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    }

    public void feedBack(String userId ,String content){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",userId);
            param.put("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.FEED_BACK),param, RequestCode.CODE_0);
    }
}
