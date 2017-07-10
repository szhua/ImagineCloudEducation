package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingDetailBean;
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
 * MeetingInfoDao
 * 获得详情；
 */

public class MeetingInfoDao extends BaseRequest {

    private MeetingDetailBean meetingDetailBean ;
    public MeetingDetailBean getMeetingDetailBean() {
        return meetingDetailBean;
    }

    public MeetingInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_0){
            meetingDetailBean = JsonUtil.node2pojo(result,MeetingDetailBean.class) ;
        }
    }
    /*type	是	string	0 会议 1项目
user_id	否	string	用户id
id	是	string	会议或项目的id
*/
    public static  final String  MEETING_TYPE = "0" ;
    public static final String PROJECT_TYPE ="1" ;
    public void getInfo(String user_id ,String type ,String id ){
        IRequestParam param =new IRequestParam();
        try {
            param.put("user_id",user_id);
            param.put("type",type) ;
            param.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MeetingInfo),param, RequestCode.CODE_0);
    }


    /*user_id	是	string	用户id
meeting_id	是	string	会议id*/
    public void addLike(String user_id ,String meeting_id ){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("meeting_id",meeting_id);
            param.put("user_id",user_id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
         basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MeetingAddLike),param,RequestCode.ADD_ZAN);
    }


    public void addFav(String user_id ,String meeting_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("meeting_id",meeting_id);
            param.put("user_id",user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MeetingAddFav),param,RequestCode.ADD_FAV);
    }




}
