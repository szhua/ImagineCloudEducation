package com.imagine.cloud.net;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;
import com.runer.net.IDao;
import com.runer.net.interf.INetResult;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * BaseRequest
 */

public abstract class BaseRequest extends IDao {

    public static  final int DEFAULT_PER_PAGE_COUNT =10;

    public int perPageCount =DEFAULT_PER_PAGE_COUNT ;

    public void setPerPageCount(int perPageCount) {
        this.perPageCount = perPageCount;
    }

    public BaseRequest(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }


     public String getReqestUrl(NetInter netInter){
         return  "?c="+netInter.getC()+"&a="+netInter.getA();
     }
     //post 请求
     public void basePostRequst(String url , IRequestParam  params ,int requestCode ){
         Logger.d(params);
         RequestParams pas =new RequestParams() ;
         pas.put("param",params.toString());
         originalPostRequest(url,pas,requestCode);
     }


}
