package com.imagine.cloud.dao;

import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.BankInfoBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import org.json.JSONException;
import java.io.IOException;

/**
 * Created by szhua on 2017/8/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * GetUserBankDao
 */

public class GetUserBankDao extends BaseRequest {

    private BankInfoBean bankInfoBean ;

    public BankInfoBean getBankInfoBean() {
        return bankInfoBean;
    }

    public GetUserBankDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.GET_USER_BANK){
            bankInfoBean = JsonUtil.node2pojo(result,BankInfoBean.class);
        }
    }
    /**/
    public void getUserBank(String user_id){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("user_id",user_id);
        } catch (JSONException e){
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.GET_USER_BANK),param, RequestCode.GET_USER_BANK);
    }
}
