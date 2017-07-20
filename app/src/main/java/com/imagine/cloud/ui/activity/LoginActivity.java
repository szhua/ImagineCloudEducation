package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.dao.LoginDao;
import com.imagine.cloud.util.AppUtil;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;
import java.util.Set;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @InjectView(R.id.edit_pass)
    EditText editPass;
    @InjectView(R.id.login_bt)
    TextView loginBt;
    @InjectView(R.id.wechat_login)
    TextView wechatLogin;
    @InjectView(R.id.qq_login)
    TextView qqLogin;
    @InjectView(R.id.sina_login)
    TextView sinaLogin;
    @InjectView(R.id.forget_pass_bt)
    TextView forgetPassBt;
    @InjectView(R.id.register_bt)
    TextView registerBt;

    private LoginDao loginDao ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginDao =new LoginDao(this,this) ;
        //loginDao.thirdLogin("wechat","oc4Mz1LxiUmUCFTW-g-0x44ULxtc");
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("登录");
    }


   private  String userName ;
   private  String password ;
    private boolean chckInput(){

        userName = editPhoneNum.getText().toString() ;
        if(TextUtils.isEmpty(userName)){
            UiUtil.showLongToast(this,"请填写您的手机号");
            return  false ;
        }
        if(!UiUtil.isValidMobileNo(userName)){
            UiUtil.showLongToast(this,"手机号格式不正确");
            return  false  ;
        }
        password =editPass.getText().toString();
        if(TextUtils.isEmpty(password)){
            UiUtil.showLongToast(this,"密码为空，请填写密码");
            return  false ;
        }
        if(password.length()>15){
            UiUtil.showLongToast(this,"密码长度最长为15位");
            return  false ;
        }
        return  true ;
    }

    private UserInfo userInfo ;

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOGIN){
         userInfo =loginDao.getUserInfo() ;
         if(userInfo!=null){
                AppUtil.setUserInfo(this,userInfo);
                UiUtil.showLongToast(this,"登录成功");
                setTagAlias();
                finish();
            }
        }else if(requestCode==RequestCode.THIRD_LOGIN){
            userInfo =loginDao.getUserInfo();
            AppUtil.setUserInfo(this,userInfo);
            UiUtil.showLongToast(this,"登录成功");
            setTagAlias();
            finish();
        }
    }
    @OnClick({R.id.login_bt, R.id.wechat_login, R.id.qq_login, R.id.sina_login ,R.id.register_bt,R.id.forget_pass_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                if(chckInput()){
                    loginDao.logIn(userName,password);
                    showProgress(true);
                }
                break;
            case R.id.wechat_login:
                if (UMShareAPI.get(this).isAuthorize(
                        this, SHARE_MEDIA.WEIXIN)) {
                    UMShareAPI.get(getApplicationContext()).deleteOauth(
                            this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
                UMShareAPI.get(getApplicationContext()).doOauthVerify(
                        this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.qq_login:
                if (UMShareAPI.get(this).isAuthorize(
                        this, SHARE_MEDIA.QQ)) {
                    UMShareAPI.get(getApplicationContext()).deleteOauth(
                            this, SHARE_MEDIA.QQ, umAuthListener);
                }
                UMShareAPI.get(getApplicationContext()).doOauthVerify(
                        this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.sina_login:
                break;
            case R.id.register_bt:
                transUi(RegisterActivity.class,null);
                break;
            case R.id.forget_pass_bt:
                transUi(ChangePassActivity.class,null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //第三方登录授权回调；
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            UiUtil.showLongToast(LoginActivity.this,"正在第三方授权");
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data){
            switch (platform) {
                case WEIXIN:
                    if (data != null) {
                        String openid = data.get("openid");
                        loginDao.thirdLogin("wechat",openid);
                        showProgressWithMsg(true,"授权成功，正在登录");
                    }
                    break;
                case QQ:
                    if (data != null) {
                        String openid = data.get("openid");
                        showProgressWithMsg(true,"授权成功，正在登录");
                    }
                    break;
                case SINA:
                    if (data != null) {
                    }
                    break;
            }
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Logger.d(t);
            UiUtil.showLongToast(LoginActivity.this,"第三方授权失败");
        }
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            UiUtil.showLongToast(LoginActivity.this,"第三方授权取消");
        }
    };

    //推送设置标签；
    private void setTagAlias(){
        //设置别名；
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, AppUtil.getUserId(this)));
    }
    public static final String TAG = "runer";
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "设置标签成功";
                    Logger.d(logs);
                    break;
                case 6002:
                    logs = "设置标签失败，60秒后重新尝试.";
                    Logger.d(logs);
                    mHandler.sendMessageDelayed(
                            mHandler.obtainMessage(MSG_SET_TAGS, tags),
                            1000 * 60);
                    break;
                default:
                    Logger.d( "Failed with errorCode = " + code);
            }
        }

    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "设置别名成功";
                    Logger.d(logs);
                    break;
                case 6002:
                    logs = "设置别名失败，60秒后重新尝试.";
                    Logger.d(logs);
                    mHandler.sendMessageDelayed(
                            mHandler.obtainMessage(MSG_SET_ALIAS, alias),
                            1000 * 60);
                    break;
                default:
                    Log.e(TAG, "Failed with errorCode = " + code);
            }
        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS= 1002;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), null,
                            (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };



}
