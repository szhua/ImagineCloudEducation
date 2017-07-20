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
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.dao.RegisterDao;
import com.imagine.cloud.dao.UploadHeaderDao;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.NormalInputEditText;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import java.io.File;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;


public class CompleteUserInfoActivity extends BaseActivity {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.header_bt)
    CircleImageView headerBt;
    @InjectView(R.id.email_input)
    NormalInputEditText emailInput;
    @InjectView(R.id.school_input)
    NormalInputEditText schoolInput;
    @InjectView(R.id.register_code)
    TextView registerCode;
    @InjectView(R.id.finish_bt)
    TextView finishBt;
    private UploadHeaderDao uploadHeaderDao ;
    private RegisterDao registerDao ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_info);
        ButterKnife.inject(this);
        uploadHeaderDao =new UploadHeaderDao(this,this) ;
        registerDao =new RegisterDao(this,this) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("完善信息");
    }

    public static final int PHOTO_SELECT_CODE = 999;
    public static final int CAMERA_TAKE_CODE = 1000;

    @OnClick({R.id.header_bt, R.id.register_code, R.id.finish_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择头像
            case R.id.header_bt:
                Album.album(CompleteUserInfoActivity.this)
                        .toolBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                        .statusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                        // .navigationBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)) // NavigationBar 颜色，默认黑色，建议使用默认。
                        .title("图库")
                        .selectCount(1)
                        .columnCount(3)
                        .camera(true)
                        .requestCode(PHOTO_SELECT_CODE)
                        .start(); // 999是请求码，返回时onActivityResult()的第一个参数。
                break;
            case R.id.register_code:

                Bundle bundle =new Bundle() ;
                bundle.putString(BaseWebAcitivity.WEB_TITLE,"用户协议");
                bundle.putString(BaseWebAcitivity.WEB_URL, Requst.REGISTER_ARGUMENT_URL);
                transUi(BaseWebAcitivity.class,bundle);

                break;
            case R.id.finish_bt:

                String email =emailInput.getInputContent();
                String schoolAddress =schoolInput.getInputContent();

                if(!TextUtils.isEmpty(email)){
                    if(!UiUtil.checkEmail(email)){
                        UiUtil.showLongToast(getApplicationContext(),"邮箱格式不正确");
                        return;
                    }
                }
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(schoolAddress)){
                    UiUtil.showLongToast(getApplicationContext(),"信息填写不完善");
                }
                registerDao.registerImproceInfo(email,schoolAddress,AppUtil.getUserId(this));
                showProgress(true);
                break;

        }
    }

    //裁剪头像的缓存地址
    public static final String CROPO_CACHE_PAHT = "imgine_cloud_crop";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SELECT_CODE) {

            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                Crop.of(Uri.fromFile(new File(pathList.get(0))), Uri.fromFile(new File(getCacheDir(), CROPO_CACHE_PAHT))).start(CompleteUserInfoActivity.this);
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
                            return Luban.with(CompleteUserInfoActivity.this).load(file).get();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
                            uploadHeaderDao.upLoadUserHeader(AppUtil.getUserId(getApplicationContext()),file);
                            showProgressWithMsg(true,"正在上传头像");
                            Picasso.with(CompleteUserInfoActivity.this).load(file).into(headerBt);
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            UiUtil.showLongToast(getApplicationContext(), "裁剪失败!");
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.UPDATE_HEADER){
            UiUtil.showLongToast(this,getString(R.string.update_header_success));
            AppUtil.setUserHeader(CompleteUserInfoActivity.this,uploadHeaderDao.getHeaderPath());
        }else if(requestCode==RequestCode.CODE_1){
            UiUtil.showLongToast(this,getString(R.string.update_user_info_success));
            finish();
        }
    }
}
