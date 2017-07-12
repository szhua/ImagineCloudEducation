package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.dao.GetUserInfoDao;
import com.imagine.cloud.dao.UploadHeaderDao;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.NormalInputEditText;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import java.io.File;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;


/*个人信息界面*/
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.username)
    NormalInputEditText usernameInput;
    @InjectView(R.id.email)
    NormalInputEditText emailInput;
    @InjectView(R.id.phone)
    NormalInputEditText phoneInput;
    @InjectView(R.id.school)
    NormalInputEditText schoolInput;
    @InjectView(R.id.level)
    NormalInputEditText level;
    @InjectView(R.id.save_bt)
    TextView saveBt;
    @InjectView(R.id.header_bt)
    CircleImageView headerBt;


    private GetUserInfoDao getUserInfoDao;
    private UploadHeaderDao uploadHeaderDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.inject(this);

        getUserInfoDao = new GetUserInfoDao(this, this);
        uploadHeaderDao =new UploadHeaderDao(this,this) ;
        getUserInfoDao.getUserInfo(AppUtil.getUserId(this));
        showProgress(true);

     saveBt.setOnClickListener(this);
        headerBt.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("个人信息");
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.GET_USER_INFO){
          UserInfo userInfo = getUserInfoDao.getUserInfo() ;
          if(userInfo!=null){
              Logger.d(userInfo);
              Picasso.with(this).load(Requst.BASE_IMG_URL+userInfo.getHead()).placeholder(R.mipmap.ic_launcher_round).into(headerBt);
              usernameInput.setRightText(userInfo.getUser_name());
              emailInput.setRightText(userInfo.getEmail());
              phoneInput.setRightText(userInfo.getMobile());
              schoolInput.setRightText(userInfo.getSchool());
          }
        }else if(requestCode==RequestCode.UPDATE_USER_UINFO){

            UiUtil.showLongToast(this,"修改信息成功");
            UserInfo userInfo =getUserInfoDao.getUserInfo();
            AppUtil.setUserInfo(this,userInfo);

            finish();
        }else if(requestCode==RequestCode.UPDATE_HEADER){
            UiUtil.showLongToast(this,"上传头像成功");
            AppUtil.setUserHeader(this,uploadHeaderDao.getHeaderPath());

        }
    }
    //选择图片
    public static final int PHOTO_SELECT_CODE = 999;
    //裁剪头像的缓存地址
    public static final String CROPO_CACHE_PAHT = "imgine_cloud_crop";
    @Override
    public void onClick(View v) {
        if (v ==headerBt){
            Album.album(this)
                    .toolBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                    .statusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                    // .navigationBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)) // NavigationBar 颜色，默认黑色，建议使用默认。
                    .title("图库")
                    .selectCount(1)
                    .columnCount(3)
                    .camera(true)
                    .requestCode(PHOTO_SELECT_CODE)
                    .start(); // 999是请求码，返回时onActivityResult()的第一个参数。
        }else if(v==saveBt){
            if(checkInput()){
                getUserInfoDao.setEmail(email);
                getUserInfoDao.setMobile(phone);
                getUserInfoDao.setUser_name(name);
                getUserInfoDao.setSchool(school);
                getUserInfoDao.upDateUserInfo(AppUtil.getUserId(this));
                showProgress(true);
            }
        }
    }
    //图像处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                Crop.of(Uri.fromFile(new File(pathList.get(0))), Uri.fromFile(new File(getCacheDir(), CROPO_CACHE_PAHT))).start(this);
            } else if (resultCode == RESULT_CANCELED) { //User canceled;

            }
            //裁剪以后的操作
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK){
            //进行压缩;
            Flowable.just(new File(getCacheDir(), CROPO_CACHE_PAHT))
                    .observeOn(Schedulers.io())
                    .map(new Function<File, File>() {
                        @Override
                        public File apply(@NonNull File file) throws Exception {
                            return Luban.with(UserInfoActivity.this).load(file).get();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
                            uploadHeaderDao.upLoadUserHeader(AppUtil.getUserId(getApplicationContext()),file);
                            Picasso.with(UserInfoActivity.this).load(file).into(headerBt);
                            showProgressWithMsg(true,"正在上传头像");
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            UiUtil.showLongToast(getApplicationContext(), "裁剪失败!");
        }
    }

    String name ;
    String email;
    String phone;
    String school;
    private boolean checkInput(){

        name =usernameInput.getInputContent() ;
        email =emailInput.getInputContent();
        phone =phoneInput.getInputContent() ;
        school =schoolInput.getInputContent();

        if(!TextUtils.isEmpty(email)){
                if(!UiUtil.checkEmail(email)){
                    UiUtil.showLongToast(this,"邮箱格式不正确");
                    return  false ;
                }
            }
        if(!TextUtils.isEmpty(phone)){
            if(!UiUtil.isValidMobileNo(phone)){
                UiUtil.showLongToast(this,"手机格式不正确");
                return  false ;
            }
        }

        return  true ;
    }
}
