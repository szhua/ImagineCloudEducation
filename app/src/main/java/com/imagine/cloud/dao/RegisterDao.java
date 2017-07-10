package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.orhanobut.logger.Logger;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * RegisterDao
 */

public class RegisterDao extends BaseRequest {

    private UserInfo userInfo ;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public RegisterDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
         if(requestCode==RequestCode.REGISTER){
             userInfo = JsonUtil.node2pojo(result,UserInfo.class) ;
         }
    }
    /*mobile	是	string	手机号
pwd	是	string	密码
code	是	string	验证码*/
    public void register(String mobile ,String pwd ,String code ,String userName ){

        IRequestParam params =new IRequestParam() ;
        try {
            params.put("pwd",pwd) ;
            params.put("code",code) ;
            params.put("mobile",mobile) ;
            params.put("user_name",userName) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.RegisterUser),params, RequestCode.REGISTER);
    }

    /*email	是	string	邮箱
     school	是	string	学校*/
    public void registerImproceInfo(String email ,String school ,String userId){
        IRequestParam params =new IRequestParam() ;
        try {
            params.put("user_id",userId) ;
            params.put("email",email);
            params.put("school",school);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.RegisterCompeteInfo),params, RequestCode.CODE_1);
    }















}
