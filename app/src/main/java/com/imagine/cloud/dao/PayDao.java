package com.imagine.cloud.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.bean.WechatPayBean;
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

/**
 * Created by szhua on 2017/7/24/024.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * PayDao
 * 支付相关的请求
 */
public class PayDao extends BaseRequest {

    private String mer_order_no ;
    public String getMer_order_no() {
        return mer_order_no;
    }
    private WechatPayBean wechatPayBean ;
    public WechatPayBean getWechatPayBean(){
        return wechatPayBean;
    }
    public PayDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.WECHAT_PAY) {
            wechatPayBean = JsonUtil.node2pojo(result, WechatPayBean.class);
            Logger.d(wechatPayBean);
        }else if(requestCode==RequestCode.BANK_PAY_GET_CODE){
           mer_order_no =result.get("mer_order_no").asText();
        }
    }

    public void wechatPay(String order_id){
        IRequestParam  param =new IRequestParam();
        try {
            param.put("order_id",order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.WECHAT_PAY),param, RequestCode.WECHAT_PAY);
    }

    /*expired_date	否	string	有效日期	信用卡必填
cvv2	否	string	背面3个数字*/
    public void bankPayGetCode(int order_id , String realname, String bank_no , String idcard , String mobile ,String expired_date ,String cvv2){
        IRequestParam  param =new IRequestParam() ;
        try {
            param.put("order_id",order_id);
            param.put("realname",realname);
            param.put("bank_no",bank_no);
            param.put("idcard",idcard);
            param.put("mobile",mobile);
            param.put("expired_date",expired_date);
            param.put("cvv2",cvv2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.BANK_PAY_GET_CODE),param, RequestCode.BANK_PAY_GET_CODE);
    }
    /*verify_code	是	string	验证码
mer_order_no	是	string	订单号*/
    public void bankPay(String verify_code ,String mer_order_no){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("verify_code",verify_code) ;
            param.put("mer_order_no",mer_order_no) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.BANK_TO_PAY),param, RequestCode.BANK_PAY);
    }
    /*申请退款*/
    public void refund(String order_id ,String reas){
        IRequestParam param =new IRequestParam() ;
        try {
            param.put("order_id",order_id);
            param.put("reson",reas);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.PAY_REFUND),param, RequestCode.RFUND);
    }
}
