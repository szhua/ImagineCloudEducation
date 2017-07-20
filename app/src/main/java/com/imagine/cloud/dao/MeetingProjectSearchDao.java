package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingProjectListBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/12/012.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingProjectSearchDao
 */

public class MeetingProjectSearchDao extends BaseRequest {
    private List<MeetingProjectListBean> meetingProjectListBeen ;

    public List<MeetingProjectListBean> getMeetingProjectListBeen() {
        return meetingProjectListBeen;
    }

    public MeetingProjectSearchDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    if(requestCode==RequestCode.CODE_0){
   meetingProjectListBeen = JsonUtil.node2pojoList(result.findValue("list"),MeetingProjectListBean.class) ;
    }
    }
    public void search(String search){
        meetingProjectListBeen =new ArrayList<>() ;
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("search",search);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MEETING_PRO_SEARCH),param, RequestCode.CODE_0);

    }
}
