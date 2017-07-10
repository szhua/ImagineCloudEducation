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
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FavListDao
 */

public class FavListDao extends BaseRequest {
    public FavListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

    }


    /*type	是	string	0 会议 1 项目
user_id	是	string	用户id*/
    public void getFavList(String type ,String user_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",user_id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.FavList),param, RequestCode.CODE_0);
    }
}
