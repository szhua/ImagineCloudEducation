package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * UserInfo
 */

public class UserInfo {

    /**
     * school :
     * id : 2
     * user_name :
     * pwd : e10adc3949ba59abbe56e057f20f883e
     * email :
     * mobile : 18854171513
     * head :
     * intro :
     * reg_date : 0000-00-00
     * state : 1
     * qq_login :
     * wechat_login :
     * weibo_login :
     * ali_login :
     * sex : 男
     * birth : 1970-01-01
     * province :
     * city :
     * area :
     * auth_state : 0
     * auth_apply_time : 0000-00-00 00:00:00
     * auth :
     * auth_fail_reason : 0
     * type
     */

    private String school;
    private String id;
    private String user_name;
    private String pwd;
    private String email;
    private String mobile;
    private String head;
    private String intro;
    private String reg_date;
    private String state;
    private String qq_login;
    private String wechat_login;
    private String weibo_login;
    private String ali_login;
    private String sex;
    private String birth;
    private String province;
    private String city;
    private String area;
    private String auth_state;
    private String auth_apply_time;
    private String auth;
    private String auth_fail_reason;
    private String type ;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQq_login() {
        return qq_login;
    }

    public void setQq_login(String qq_login) {
        this.qq_login = qq_login;
    }

    public String getWechat_login() {
        return wechat_login;
    }

    public void setWechat_login(String wechat_login) {
        this.wechat_login = wechat_login;
    }

    public String getWeibo_login() {
        return weibo_login;
    }

    public void setWeibo_login(String weibo_login) {
        this.weibo_login = weibo_login;
    }

    public String getAli_login() {
        return ali_login;
    }

    public void setAli_login(String ali_login) {
        this.ali_login = ali_login;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAuth_state() {
        return auth_state;
    }

    public void setAuth_state(String auth_state) {
        this.auth_state = auth_state;
    }

    public String getAuth_apply_time() {
        return auth_apply_time;
    }

    public void setAuth_apply_time(String auth_apply_time) {
        this.auth_apply_time = auth_apply_time;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth_fail_reason() {
        return auth_fail_reason;
    }

    public void setAuth_fail_reason(String auth_fail_reason) {
        this.auth_fail_reason = auth_fail_reason;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "school='" + school + '\'' +
                ", id='" + id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", head='" + head + '\'' +
                ", intro='" + intro + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", state='" + state + '\'' +
                ", qq_login='" + qq_login + '\'' +
                ", wechat_login='" + wechat_login + '\'' +
                ", weibo_login='" + weibo_login + '\'' +
                ", ali_login='" + ali_login + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", auth_state='" + auth_state + '\'' +
                ", auth_apply_time='" + auth_apply_time + '\'' +
                ", auth='" + auth + '\'' +
                ", auth_fail_reason='" + auth_fail_reason + '\'' +
                '}';
    }
}
