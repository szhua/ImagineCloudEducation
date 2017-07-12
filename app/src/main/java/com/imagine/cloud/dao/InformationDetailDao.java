package com.imagine.cloud.dao;

import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.net.BaseRequest;
import com.runer.net.interf.INetResult;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * InformationDetailDao
 * 请求资讯详情
 */
public class InformationDetailDao extends BaseRequest {
    public InformationDetailDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

    }
}
