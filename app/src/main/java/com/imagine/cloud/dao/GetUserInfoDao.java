package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.UserInfo;
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
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * GetUserInfoDao
 * 获得个人信息详情；
 */

public class GetUserInfoDao extends BaseRequest {

    private UserInfo  userInfo ;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public GetUserInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.GET_USER_INFO){
            userInfo = JsonUtil.node2pojo(result,UserInfo.class) ;
        }
    }

    public void getUserInfo(String user_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",user_id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.UserInfo),param, RequestCode.GET_USER_INFO);
    }

    /*user_id	是	int	用户id
user_name	否	string	姓名
mobile	否	string	手机号
email	否	string	邮箱
address	否	string	地址
school	否	string	学校
post	否	string	职务*/
    private String user_name ;
    String mobile ;
    String email ;
    String address ;
    String school ;
    String post ;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setPost(String post) {
        this.post = post;
    }
    public void upDateUserInfo(String user_id ){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",user_id) ;
            param.put("mobile",mobile) ;
            param.put("email",email);
            param.put("address",address) ;
            param.put("school",school) ;
            param.put("post",post) ;
            param.put("user_name",user_name) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
       basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.EditUserInfo),param,RequestCode.UPDATE_USER_UINFO);
    }



}
