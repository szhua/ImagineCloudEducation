package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.bean.SchoolBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.RebackCode;
import com.imagine.cloud.net.Requst;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/21/021.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * GetSchoolDao
 */

public class GetSchoolDao extends BaseRequest {
    public GetSchoolDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_1){
            totalPage =result.findValue("total_page").asInt();
            List<SchoolBean> resultList = JsonUtil.node2pojoList(result.findValue("list"),SchoolBean.class);
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




    private int totalPage ;
    private List<SchoolBean> datas  =new ArrayList<>();
    public List<SchoolBean> getDatas() {
        return datas;
    }

    public  boolean hasMore(){
        return  isMore ;
    }


    private boolean isMore;
    private int currentPage =1;

    /*num	是	string	每页数量
      page	是	string	当前页数*/
    public void getSchoolList(String key){
        IRequestParam param =new IRequestParam();
        try {
            param.put("school",key) ;
            param.put("page",currentPage) ;
            param.put("num",perPageCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.GET_SCHOOL),param, RequestCode.CODE_1);
    }

    public void refresh(String key){
        datas=new ArrayList<>();
        currentPage =1 ;
        getSchoolList(key);
    }


    public void nextPage(String key){
        if(hasMore()){
            currentPage++ ;
            getSchoolList(key);
        }
    }
}
