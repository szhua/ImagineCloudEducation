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
    MeetingList("meeting","meeting_list","会议列表"),
    UserInfo("user","user_info","获得用户信息"),
    EDIT_PASS("user","edit_pwd","修改用户密码"),
    MeetingInfo("meeting","info","获取详情"),
    MeetingAddLike("meeting","add_like","点赞"),
    MeetingDeleFav("meeting","del_fav","取消收藏"),
    MeetingAddFav("meeting","add_fav","收藏"),
    FavList("meeting","fav_list","收藏列表"),
    BannerList("banner","banner_list","轮播图"),
    MEETIN_PROJECT_LIST("meeting_project","meeting_project_list","会议项目资讯列表"),
    FEED_BACK("opinion","add","意见反馈"),
    MEETING_PRO_Ifo("meeting_project","info","资讯详情"),
    MEETING_PRO_SEARCH("meeting_project","search","资讯搜索"),
    COURSE_AUDIO_LIST("voice","voice_list","音频课程列表"),
    VOICE_COURSE_INFO("voice","info","音频视频详情"),
    MESSAGE_LIST("message","message_list","消息列表"),
    MESSAGE_INFO("message","info","消息详情"),
    MESSAGE_DELE("message","del","删除消息"),
    ADD_ORDER("buy","add","添加订单"),
    CHECK_MSG_READ("message","unread","检测未读消息"),
    SET_MSG_READ("message","read","设为已读"),
    MY_MEETING_LIST("buy","my_buy","我的会议"),
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
