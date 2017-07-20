package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/13/013.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AlipayBean
 */

public class AlipayBean {
    /*  .setPartner(partner) //商户PID || 签约合作者身份ID
                .setSeller(seller)  // 商户收款账号 || 签约卖家支付宝账号
                .setOutTradeNo(outTradeNo) //设置唯一订单号
                .setSubject(orderSubject) //设置订单标题
                .setBody(orderBody) //设置订单内容
                .setPrice(price) //设置订单价格
                .setCallbackUrl(callbackUrl) //设置回调链接*/

String partner ;
    String seller ;
    String outTradeNo ;
    String orderSubject ;
    String orderBody ;
    String price ;
    String callbackUrl ;


    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
