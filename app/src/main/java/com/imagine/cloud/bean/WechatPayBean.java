package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/13/013.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * WechatPayBean
 */

public class WechatPayBean {


    /*.setAppId(appid) //微信支付AppID
                .setPartnerId(partnerid)//微信支付商户号
                .setPrepayId(prepayid)//预支付码
                .setPackageValue("Sign=WXPay")//"Sign=WXPay"
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)//时间戳
                .setSign(sign)//签名*/
    String partnerid ;
    String noncestr ;
    String timestamp ;
    String sign ;
   String prepayid ;

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
