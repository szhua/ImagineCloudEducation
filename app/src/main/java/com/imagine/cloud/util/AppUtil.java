package com.imagine.cloud.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

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


}
