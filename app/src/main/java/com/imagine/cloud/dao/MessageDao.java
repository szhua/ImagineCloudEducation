package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.bean.MeetingDetailBean;
import com.imagine.cloud.bean.MessageBean;
import com.imagine.cloud.bean.MessageDetailBean;
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

import static com.imagine.cloud.net.NetInter.CHECK_MSG_READ;

/**
 * Created by szhua on 2017/7/15/015.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MessageDao
 *
 * 消息相关的接口
 */

public class MessageDao extends BaseRequest{
    public MessageDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }




private MessageDetailBean messageDetailBean ;

    //获得消息详情
    public MessageDetailBean getMessageDetailBean() {
        return messageDetailBean;
    }

    private int totalPage ;
    private List<MessageBean> datas  =new ArrayList<>();
    public List<MessageBean> getDatas() {
        return datas;
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode== RequestCode.CODE_1){
            totalPage =result.findValue("total_page").asInt();
            List<MessageBean> resultList = JsonUtil.node2pojoList(result.findValue("list"),MessageBean.class);
            if(resultList!=null&&!resultList.isEmpty()){
                datas.addAll(resultList);
                if(totalPage>currentPage){
                    isMore =true ;
                }else{
                    isMore =false;
                }
            }
        }else if(requestCode==RequestCode.CODE_2){
            messageDetailBean =JsonUtil.node2pojo(result, MessageDetailBean.class);
        }else if(requestCode==RequestCode.CHECK_MSG_READ){
            if("0".equals(result.asText())){
                hasMsg =true ;
            }else{
                hasMsg =false ;
            }
        }
    }
    public  boolean hasMore(){
        return  isMore ;
    }

    private boolean isMore;
    private int currentPage =1;

    public void refresh(String key){
        datas=new ArrayList<>();
        currentPage =1 ;
        getMessageList(key);
    }


    public void nextPage(String key){
        if(hasMore()){
            currentPage++ ;
            getMessageList(key);
        }
    }

    public void getMessageList(String user_id ){
        IRequestParam param =new IRequestParam();
        try {
            param.put("user_id",user_id) ;
            param.put("page",currentPage) ;
            param.put("num",perPageCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.d(param);
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MESSAGE_LIST),param, RequestCode.CODE_1);
    }


    public void getMeesageInfo(String messageId){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("id",messageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MESSAGE_INFO),param, RequestCode.CODE_2);
    }

    public void delMessage(String message_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("id",message_id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.MESSAGE_DELE),param,RequestCode.CODE_3);
    }


    private boolean hasMsg ;


    public boolean isHasMsg() {
        return hasMsg;
    }

    public void checkMsg(String user_id){
        hasMsg =false ;
        IRequestParam param  =new IRequestParam() ;
        try {
            param.put("user_id",user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(CHECK_MSG_READ),param,RequestCode.CHECK_MSG_READ);
    }

    public void setMsgRead(String msgId){

        IRequestParam param =new IRequestParam() ;
        try {
            param.put("id",msgId) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.SET_MSG_READ),param,RequestCode.SET_MSG_READ);
    }



}
