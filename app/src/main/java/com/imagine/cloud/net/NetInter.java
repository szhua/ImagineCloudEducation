package com.imagine.cloud.net;

/**
 * Created by szhua on 2017/7/4/004.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * NetInter
 */

//请求接口
public enum  NetInter {

    LogIn("user","login","登录"),
    RegisterUser("user","reg","注册"),
    SendCode("mobile_code","add","发送验证码"),
    UpHeader("reg_perfect","head","上传用户头像"),
    RegisterCompeteInfo("reg_perfect","add","注册完善用户信息"),
    ThirdLogIn("user","open_login","第三方登录"),
    MeetingList("meeting_project","meeting_project_list","会议列表"),
    UserInfo("user","user_info","获得用户信息"),
    EDIT_PASS("user","edit_pwd","修改用户密码"),
    MeetingInfo("meeting_project","info","获取详情"),
    MeetingAddLike("meeting_project","add_like","点赞"),
    MeetingAddFav("meeting_project","add_fav","收藏"),
    FavList("meeting_project","fav_list","收藏列表"),
    EditUserInfo("user","user_update","完善用户信息");

 private String a;
 private String c;
    private String des ;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    NetInter(String c , String a , String des){
this.c =c ;
     this.a =a ;
     this.des =des ;
}


}
