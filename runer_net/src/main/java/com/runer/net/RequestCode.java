package com.runer.net;

/**
 * http请求编号，由于像登录这种接口，可能会在不同的地方调用，
 * 因此需要设置独特的编号，已防止与其他编号冲突,独特编号从100开始
 * Create by szhua 2016/3/11
 */
public class RequestCode {


    public final static int CODE_0 = 0;
    public final static int CODE_1 = 1;
    public final static int CODE_2 = 2;
    public final static int CODE_3 = 3;
    public final static int CODE_4 = 4;
    public final static int CODE_5 =5 ;
    public final static int CODE_6= 6;

    public final static int CODE_SUCCESS =0;
    public final static  int SEND_CODE =11;
    public final static  int LOGIN=12 ;
    public final static  int REGISTER =14;
    public final static  int GET_USER_INFO =15;
    public final static  int UPDATE_HEADER =13;
    public static  final  int UPDATE_USER_UINFO =16;
    public static  final int CHANGE_PASS =17;
    public static  final int ADD_ZAN =18;
    public static  final int ADD_FAV =19;
    public static  final int DEL_FAV =20;
    public static  final int GET_BANNER =21 ;
    public static  final int CHECK_MSG_READ =22 ;
    public static  final int SET_MSG_READ =23;
    public static  final int  THIRD_LOGIN =24;




}