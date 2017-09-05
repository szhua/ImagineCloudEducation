package com.imagine.cloud.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/7/28/028.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AddOrderResultBean
 */

public class AddOrderResultBean implements Serializable {

    /**
     * id : 31
     * title : 1111
     * mer_order_no : 201707284889
     * create_time : 2017-07-28 09:13:23
     * price : 222
     */

    private int id;
    private String title;
    private String mer_order_no;
    private String create_time;
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMer_order_no() {
        return mer_order_no;
    }

    public void setMer_order_no(String mer_order_no) {
        this.mer_order_no = mer_order_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
