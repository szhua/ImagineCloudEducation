package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.IDao;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * LoginDao
 */

public class LoginDao extends BaseRequest {


    private UserInfo userInfo;
    public UserInfo getUserInfo(){
        return userInfo;
    }
    public LoginDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        userInfo = JsonUtil.node2pojo(result,UserInfo.class) ;
    }
    public void logIn(String phone ,String pass){
        IRequestParam params =new IRequestParam() ;
        try {
            params.put("mobile",phone) ;
            params.put("pwd", pass) ;
        } catch (JSONException e){
            e.printStackTrace();
        }
        Logger.d(params);
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.LogIn),params, RequestCode.LOGIN);

    }
}
