package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/17/017.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AddOrderBean
 */

public class AddOrderBean {

    /*    /*title	是	string	会议名称
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

    String id ;
    String name ;
    String mobile ;
    String email ;
    String sex ;
    String nation ;
    String school ;
    String living ;
    String header ;
    String num ;
    String address ;
    String bank ;
    String bank_num ;
    String other ;
    String user_id ;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
