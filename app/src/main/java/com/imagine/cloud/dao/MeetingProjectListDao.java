package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.MeetingBean;
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
 * MeetingProjectListDao
 */

//获取资讯中的会议和项目列表
public class MeetingProjectListDao extends BaseRequest {


    private List<MeetingProjectListBean> data =new ArrayList<>();
    private int  totalPage;

    public List<MeetingProjectListBean> getData() {
        return data;
    }

    public MeetingProjectListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode==RequestCode.CODE_0){
            totalPage =result.findValue("total_page").asInt();
            List<MeetingProjectListBean> resultList = JsonUtil.node2pojoList(result.findValue("list"),MeetingProjectListBean.class);
            if(resultList!=null&&!resultList.isEmpty()){
                data.addAll(resultList);
                if(totalPage>currentPage){
                    isMore =true ;
                }else{
                    isMore =false;
                }
            }
        }
    }

    //获取资讯中的会议和项目列表
    /*type	是	string	0 会议列表 1 国家教育 2社科 3 自科 4 其他
hot	否	string	会议热度排序时填写的数据 值是 0
num	是	string	每页数量
page	是	string	当前页数*/


    public boolean hasMore() {
        return isMore;
    }

    private boolean isMore;
    private int currentPage = 1;


    public static final String MEETING_SEARCH_TYPE = "0";
    public static final String EDU_SEARCH_TYPE = "1";
    public static final String SCO_SEARCH_TYPE = "2";
    public static final String NATURE_SEARCH_TYPE = "3";
    public static final String OTHER_SEARCH_TYPE = "4";

    public void getMeetingProjectList(String type, String hot) {
        IRequestParam param = new IRequestParam();
        try {
            param.put("type", type);
            param.put("hot", hot);
            param.put("num", DEFAULT_PER_PAGE_COUNT);
            param.put("page", currentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL + getReqestUrl(NetInter.MEETIN_PROJECT_LIST), param, RequestCode.CODE_0);
    }


    public void refresh(String type, String hot) {
        data = new ArrayList<>();
        currentPage = 1;
        getMeetingProjectList(type, hot);
    }


    public void nextPage(String type, String hot) {
        if (hasMore()) {
            currentPage++;
            getMeetingProjectList(type, hot);
        }
    }


}
