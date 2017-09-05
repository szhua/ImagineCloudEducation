package com.imagine.cloud.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/7/29/029.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * OrderInfoBean
 */

public class OrderInfoBean implements Serializable{


    /**
     * tongzhi : http://192.168.0.85/changxiang/api/tongzhi.php?id=88
     * ziliao : http://192.168.0.85/changxiang/api/ziliao.php?id=88
     * richeng : http://192.168.0.85/changxiang/api/richeng.php?id=88
     * fapiao : http://192.168.0.85/changxiang/api/fapiao.php?order_id=76
     */

    private String tongzhi;
    private String ziliao;
    private String richeng;
    private String fapiao;

    /*{"pay_type":"0","pay_status":"3","refund_time":"0000-00-00 00:00:00"*/
    private String pay_type ;
    private String pay_status ;
    private String refund_time ;
    private String buchong ;


    public String getBuchong() {
        return buchong;
    }

    public void setBuchong(String buchong) {
        this.buchong = buchong;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    public String getTongzhi() {
        return tongzhi;
    }

    public void setTongzhi(String tongzhi) {
        this.tongzhi = tongzhi;
    }

    public String getZiliao() {
        return ziliao;
    }

    public void setZiliao(String ziliao) {
        this.ziliao = ziliao;
    }

    public String getRicheng() {
        return richeng;
    }

    public void setRicheng(String richeng) {
        this.richeng = richeng;
    }

    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
    }


    @Override
    public String toString() {
        return "OrderInfoBean{" +
                "tongzhi='" + tongzhi + '\'' +
                ", ziliao='" + ziliao + '\'' +
                ", richeng='" + richeng + '\'' +
                ", fapiao='" + fapiao + '\'' +
                '}';
    }
}
