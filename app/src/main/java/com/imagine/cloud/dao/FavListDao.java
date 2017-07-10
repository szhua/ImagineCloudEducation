package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FavListDao
 */

public class FavListDao extends BaseRequest {


    private List<MeetingBean> meetingBeanList ;


    public FavListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_1){
            totalPage =result.findValue("total_page").asInt();
            List<MeetingBean> resultList = JsonUtil.node2pojoList(result.findValue("list"),MeetingBean.class);
            if(resultList!=null&&!resultList.isEmpty()){
                datas.addAll(resultList);
                if(totalPage>currentPage){
                    isMore =true ;
                }else{
                    isMore =false;
                }
            }
        }
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
