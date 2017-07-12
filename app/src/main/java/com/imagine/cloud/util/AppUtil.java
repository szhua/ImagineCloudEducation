package com.imagine.cloud.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.ui.activity.LoginActivity;
import com.runer.liabary.util.Prefs;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by szhua on 2017/7/4/004.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AppUtil
 */

public class AppUtil {

    public static UserInfo getUserInfo(Context context){
        UserInfo userInfo =new UserInfo() ;
        userInfo.setId(Prefs.with(context).read("user_id"));
        userInfo.setHead(Prefs.with(context).read("user_head"));
        userInfo.setUser_name(Prefs.with(context).read("user_name"));
        return  userInfo ;
    }

    public static void setUserHeader(Context context ,String userHeader){
        Prefs.with(context).write("user_head",userHeader);
    }

    public static void setUserInfo(Context context ,UserInfo userInfo){
       // if(TextUtils.isEmpty(userInfo.getId()))
        Prefs.with(context).write("user_id",userInfo.getId());
      //  if(TextUtils.isEmpty(userInfo.getUser_name()))
            Prefs.with(context).write("user_name",userInfo.getUser_name());
       // if(TextUtils.isEmpty(userInfo.getHead()))
            Prefs.with(context).write("user_head",userInfo.getHead());
    }

    //获取用户id
    public static  String getUserId(Context context){
        return Prefs.with(context).read("user_id");
    }
    //设置用户id
    public static  void setUserId(Context context,String user_id){
        Prefs.with(context).write("user_id",user_id);
    }

    /*是否登录*/
    public boolean chckeLogin(Context context ,boolean isToLog){

        if(TextUtils.isEmpty(getUserId(context))){
            //去登陆
            if(isToLog){
                Intent intent =new Intent(context, LoginActivity.class) ;
                context.startActivity(intent);
            }
            return  false ;
        }else{
            return  true ;
        }
    }


    public static List<String> getTestData(){
        ArrayList<String> data =new ArrayList();
        for (int i = 0; i <20; i++) {
            if(i==1) {
                data.add("itemUnit" + i);
            }else{
                data.add("item"+i);
        }
            }
        return  data ;
    }


    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }


}
