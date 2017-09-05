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
    String noncestr;
    String timestamp ;
    String sign ;
    String prepayid ;
    String appid;
    /*   /**
     * appid : wx5c0a77ba63b92ae5
     * partnerid : 1486197972
     * prepayid : wx201707271122111f077b3d890976396903
     * noncestr : snn73peq8czn99p61c21op0gjandoti6
     * timestamp : 1501125551
     * sign : 4EA83FB740BAD8F267CAD48FE749D873
     */
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

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

    @Override
    public String toString() {
        return "WechatPayBean{" +
                "partnerid='" + partnerid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
