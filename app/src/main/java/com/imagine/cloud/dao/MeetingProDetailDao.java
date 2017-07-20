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
 * Created by szhua on 2017/7/12/012.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingProDetailDao
 */

public class MeetingProDetailDao extends BaseRequest{

    private MeetingDetailBean detail ;

    public MeetingDetailBean getDetail() {
        return detail;
    }

    public MeetingProDetailDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_0) {
            detail = JsonUtil.node2pojo(result, MeetingDetailBean.class);
        }
    }

    public static  final String MEETING_INFO_TYPE ="0";
    public static  final String PRO_INFO_TYPE ="1";

    public void getInfo(String type ,String user_id ,String id ){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("type",type);
            param.put("user_id",user_id) ;
            param.put("id",id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MEETING_PRO_Ifo),param, RequestCode.CODE_0);
    }
}
