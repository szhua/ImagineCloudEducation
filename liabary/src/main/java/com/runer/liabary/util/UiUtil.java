package com.runer.liabary.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runer.liabary.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pcdd
 * Create   2017/3/14 10:48;
 * https://github.com/szhua
 * @author sz.hua
 */
public class UiUtil {



    //为textView设置image;
    public  static  void setTextRightImage(TextView textView ,int drawable){
        Drawable nav_up= ContextCompat.getDrawable(textView.getContext(),drawable);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(null, null, nav_up, null);
    }
    public  static  void setTextTopImage(TextView textView ,int drawable){
        Drawable nav_up= ContextCompat.getDrawable(textView.getContext(),drawable);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(null, nav_up, null, null);
    }
    public  static  void setTextBottomImage(TextView textView ,int drawable){
        Drawable nav_up= ContextCompat.getDrawable(textView.getContext(),drawable);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(null, null, null, nav_up);
    }
    public  static  void setTextRLeftImage(TextView textView ,int drawable){
        Drawable nav_up= ContextCompat.getDrawable(textView.getContext(),drawable);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(nav_up, null, null, null);
    }


    /**
     * 自定义toast样式
     *
     * @param context
     * @param message
     */

    public static Toast toast;

    public static void showLongToast(Context context, String message) {
        if (context == null) {
            return;
        }
        //这样做的原因是连续的出现Toast ；
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textMsg = (TextView)view.findViewById(R.id.toastMsg);
        textMsg.setText(message);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 在屏幕中间的toast*
     * @param context
     * @param message
     */
    public static void showLongToastCenter(Context context, String message) {
        if (context == null) {
            return;
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_center, null);
        TextView textMsg = (TextView) view.findViewById(R.id.toastMsg);
        textMsg.setText(message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    /*
     * 验证手机号码，（请自觉使用规范的正则表达式）
     *
     * @param mobileNo
     * @return
     */
    public static boolean isValidMobileNo(String mobileNo) {
        boolean flag = false;
        // Pattern p = Pattern.compile("^(1[358][13567890])(\\d{8})$");
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher match = p.matcher(mobileNo);
        if (mobileNo != null) {
            flag = match.matches();
        }
        return flag;
    }


    /**
     * 隐藏从底部弹出的view
     *
     * @param context
     * @param v
     */
    public static void hide_menu_alpha(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.GONE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.alpha_out));
    }


    /**
     * Scale in ；
     *
     * @param context
     * @param v
     */
    public static void show_menu_scale(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.VISIBLE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.scale_in));
    }

    /**
     * Scale in ；
     * @param context
     * @param v
     */
    public static void hide_menu_scale(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.GONE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.scale_out));
    }


    /**
     * 渐变显示menu ；
     *
     * @param context
     * @param v
     */
    public static void show_menu_alpha(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.VISIBLE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.alpha_in));


    }


    /**
     * 隐藏从底部弹出的view
     *
     * @param context
     * @param v
     */
    public static void hide_menu(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.GONE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_top_out));
    }

    /**
     * 从底部弹出view
     *
     * @param context
     * @param v
     */
    public static void show_menu(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        v.setVisibility(View.VISIBLE);
        v.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_top_in));
    }


    /**
     * 正则表达式校验邮箱
     * @param emaile 待匹配的邮箱
     * @return 匹配成功返回true 否则返回false;
     */
    public static boolean checkEmail(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }



    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }




}
