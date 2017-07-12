package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.loopj.android.http.RequestParams;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * UploadHeaderDao
 */

public class UploadHeaderDao extends BaseRequest {
    String headerPath ;

    public String getHeaderPath() {
        return headerPath;
    }
    public UploadHeaderDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        headerPath =result.asText() ;
    }

    public void upLoadUserHeader(String userId ,File headerImg){
        try {
            IRequestParam param =new IRequestParam() ;
            param.put("user_id",userId) ;
            RequestParams params =new RequestParams() ;
            params.put("param",param.toString());
            //设置头像：
            try {
                params.put("head",headerImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            originalPostRequest(Requst.BASE_URL+getReqestUrl(NetInter.UpHeader),params, RequestCode.UPDATE_HEADER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
