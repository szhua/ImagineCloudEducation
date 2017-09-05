package com.imagine.cloud.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.imagine.cloud.Manifest;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.ShareBean;
import com.imagine.cloud.ui.activity.MeetingDetailActivity;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by szhua on 2017/8/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * ShareUtil
 */

public class ShareUtil {


    private  Context mContext ;
    public static ShareUtil getInstance(Context context){
        ShareUtil shareUtil =new ShareUtil() ;
        shareUtil.mContext =context ;
        return  shareUtil ;
    }

    public  void share(final ShareBean shareBean, final BaseActivity activity){

        //android6.0以上的时候
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", };
            ActivityCompat.requestPermissions(activity,mPermissionList,123);
        }
        Flowable.just(activity)
                .subscribeOn(Schedulers.io())
                .map(new Function<BaseActivity,Bitmap>(){
                    @Override
                    public Bitmap apply(BaseActivity meetingDetailActivity) throws Exception {
                        Bitmap bmp= BitmapFactory.decodeResource(meetingDetailActivity.getResources(),shareBean.getDefaultImage());
                        try{
                            Bitmap a = Picasso.with(meetingDetailActivity).load(shareBean.getImgUrl()).get();
                            if(a!=null){
                                bmp =a ;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return bmp;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        UMWeb  web = new UMWeb(shareBean.getUrl());
                        web.setTitle(shareBean.getTitle());//标题\
                        web.setDescription(shareBean.getDes());//描述
                        UMImage thumb =new UMImage(activity,bitmap);
                        web.setThumb(thumb);//缩略图
                        new ShareAction(activity).withMedia(web)
                                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE)
                                .setCallback(shareListener)
                                .open();
                    }
                });
    }

     private  UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示
            UiUtil.showLongToast(mContext,"分享开始");
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            UiUtil.showLongToast(mContext,"分享成功");
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            UiUtil.showLongToast(mContext,"分享失败");
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UiUtil.showLongToast(mContext,"分享取消");
        }
    };


}
