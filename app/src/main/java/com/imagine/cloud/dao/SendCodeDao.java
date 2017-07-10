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
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * SendCodeDao
 */

public class SendCodeDao extends BaseRequest {
    public SendCodeDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

    }

    /*mobile 是	string	手机号
 type	是	string	类型。login，reg
 reg 注册 edit 修改密码
 */
    public static  final String CHANGE_PASS_TYPE ="edit";
    public static  final String REGIST_TYPE ="reg";
    public void sendCode(String mobile ,String type ){

        IRequestParam params =new IRequestParam() ;
        try {
            params.put("mobile",mobile);
            params.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.SendCode),params, RequestCode.SEND_CODE);
    }
}
