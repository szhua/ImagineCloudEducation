package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/8/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * BankInfoBean
 */

public class BankInfoBean {


    /**
     * id : 3
     * bank_no : 6212261602018757301
     * id_card : 371502199111113813
     * name : 单志华
     * mobile : 18854171513
     * user_id : 54
     * cvv2 :
     * expired_date :
     */

    private String id;
    private String bank_no;
    private String id_card;
    private String name;
    private String mobile;
    private String user_id;
    private String cvv2;
    private String expired_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }
}
