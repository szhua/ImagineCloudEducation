package com.imagine.cloud.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/7/20/020.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingOrderBean
 */

public class MeetingOrderBean implements Serializable{
    /**
     * id : 51
     * create_time :
     * title : 22017-7-184特瑞
     * subtitle : ,如果两个数组存在相同的key,后面的一个会覆盖前面的 ,如果两个数组存在相同的key,后面的一个会覆盖前面的
     * status : 0
     */
    private String id;
    private String order_id ;
    private String create_time;
    private String title;
    private String subtitle;
    private String status;
    private String img  ;
    /**
     * pay_status : 0
     * pay_type : 0
     * refund_time : 0000-00-00 00:00:00
     */

    private String pay_status;
    private String pay_type;
    private String refund_time;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeetingOrderBean{" +
                "id='" + id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }
}
