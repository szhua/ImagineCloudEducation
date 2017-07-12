package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
         }
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
                break;
            case R.id.qq_login:
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
}
