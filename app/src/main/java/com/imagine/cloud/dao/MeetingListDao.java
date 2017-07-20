package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingBean;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingListDao
 */
public class MeetingListDao extends BaseRequest {

    private int totalPage ;
    private List<MeetingBean> datas  =new ArrayList<>();
    public List<MeetingBean> getDatas() {
        return datas;
    }

    public MeetingListDao(Context context, INetResult iNetResult) {
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
    public  boolean hasMore(){
        return  isMore ;
    }


    private boolean isMore;
    private int currentPage =1;

    /*num	是	string	每页数量
      page	是	string	当前页数*/
    public void getMeetingList(String key){
        IRequestParam param =new IRequestParam();
        try {
            param.put("type","0");
            param.put("search",key) ;
            param.put("page",currentPage) ;
            param.put("num",perPageCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MeetingList),param, RequestCode.CODE_1);
    }

    public void refresh(String key){
        datas=new ArrayList<>();
        currentPage =1 ;
        getMeetingList(key);
    }


    public void nextPage(String key){
        if(hasMore()){
            currentPage++ ;
            getMeetingList(key);
        }
    }
}
