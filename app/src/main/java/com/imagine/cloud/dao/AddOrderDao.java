package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.AddOrderBean;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.orhanobut.logger.Logger;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/17/017.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AddOrderDao
 */

public class AddOrderDao extends BaseRequest {
    String orderId ;


    public String getOrderId() {
        return orderId;
    }

    public AddOrderDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_0){
         orderId =result.asText();
        }
    }


    /*id	是	string	会议会议id;
name	是	string	姓名
mobile	否	string	手机号
email	是	string	邮箱
sex	是	string	性别
nation	否	string	民族
school	是	string	学校
living	是	string	住宿 单住 合住
header	否	string	发票抬头
num	是	string	纳税人识别号
address	是	string	地址
bank	否	string	开户行
bank_num	否	string	银行账户
other	否	string	其他要求*/


    public void addOrder(AddOrderBean addOrderBean){

        IRequestParam param =new IRequestParam();
        try {
            param.put("id",addOrderBean.getId());
            param.put("name",addOrderBean.getName());
            param.put("email",addOrderBean.getEmail());
            param.put("sex",addOrderBean.getSex());
            param.put("nation",addOrderBean.getNation());
            param.put("school",addOrderBean.getSchool()) ;
            param.put("living",addOrderBean.getLiving()) ;
            param.put("header",addOrderBean.getHeader()) ;
            param.put("num",addOrderBean.getNum());
            param.put("address",addOrderBean.getAddress()) ;
            param.put("bank",addOrderBean.getAddress()) ;
            param.put("bank_num",addOrderBean.getBank_num());
            param.put("other",addOrderBean.getOther()) ;
            param.put("user_id",addOrderBean.getUser_id());
            Logger.d(param);

        } catch (JSONException e) {
            e.printStackTrace();
        }
basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.ADD_ORDER),param, RequestCode.CODE_0);

    }
}
