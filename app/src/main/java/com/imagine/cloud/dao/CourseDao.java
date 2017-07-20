package com.imagine.cloud.dao;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.CourseBean;
import com.imagine.cloud.bean.CourseDetailBean;
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
 * Created by szhua on 2017/7/13/013.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseDao
 */

public class CourseDao extends BaseRequest {



    private CourseDetailBean courseDetailBean ;
    private int totalPage ;
    private List<CourseBean> datas  =new ArrayList<>();
    public List<CourseBean> getDatas() {
        return datas;
    }

    public CourseDetailBean getCourseDetailBean() {
        return courseDetailBean;
    }

    public CourseDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode== RequestCode.CODE_1){
            totalPage =result.findValue("total_page").asInt();
            List<CourseBean> resultList = JsonUtil.node2pojoList(result.findValue("list"),CourseBean.class);
            if(resultList!=null&&!resultList.isEmpty()){
                datas.addAll(resultList);
                if(totalPage>currentPage){
                    isMore =true ;
                }else{
                    isMore =false;
                }
            }
        }else if(requestCode==RequestCode.CODE_0){
            courseDetailBean =JsonUtil.node2pojo(result,CourseDetailBean.class) ;
        }
    }
    public void getCoursesForAudio(){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("page",currentPage) ;
            param.put("num",perPageCount) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.COURSE_AUDIO_LIST),param,RequestCode.CODE_1);
    }


    public void getAudioCourseInfo(String id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("id",id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.VOICE_COURSE_INFO),param,RequestCode.CODE_0);

    }


    public  boolean hasMore(){
        return  isMore ;
    }


    private boolean isMore;
    private int currentPage =1;


    public void refresh(){
        datas=new ArrayList<>();
        currentPage =1 ;
        getCoursesForAudio();
    }


    public void nextPage(){
        if(hasMore()){
            currentPage++ ;
            getCoursesForAudio();
        }
    }
}
