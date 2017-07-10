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
 * EidtPassDao
 */

public class EidtPassDao extends BaseRequest {
    public EidtPassDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    }
    /*mobile	是	string	手机号
code	是	string	验证码
new_pwd	是	string	新密码*/
    public void eidtPass(String mobile ,String code ,String new_pwd){

        IRequestParam  param =new IRequestParam() ;
        try {
            param.put("mobile",mobile) ;
            param.put("code",code );
            param.put("new_pwd",new_pwd) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.EDIT_PASS),param, RequestCode.CHANGE_PASS);
    }
}
