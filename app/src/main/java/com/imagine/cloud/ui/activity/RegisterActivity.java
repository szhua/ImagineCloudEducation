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
import com.imagine.cloud.dao.RegisterDao;
import com.imagine.cloud.dao.SendCodeDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.CodeButton;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

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
    @InjectView(R.id.edit_name)
    EditText editName;
    @InjectView(R.id.edit_pass)
    EditText editPass;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.next_bt)
    TextView nextBt;
    @InjectView(R.id.edit_pass_confirm)
    EditText editPassConfirm;

    private RegisterDao registerDao;
    private SendCodeDao sendCodeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        registerDao = new RegisterDao(this, this);
        sendCodeDao = new SendCodeDao(this, this);
    }


    private String phone;
    private String userName;
    private String pass;

    public boolean checkInputFirst() {
        phone = editPhoneNum.getText().toString();
        userName = editName.getText().toString();
        pass = editPass.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            UiUtil.showLongToast(this, "请输入手机号");
            return false;
        }
        if (!UiUtil.isValidMobileNo(phone)) {
            UiUtil.showLongToast(this, "输入的手机格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            UiUtil.showLongToast(this, "请输入您的姓名");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            UiUtil.showLongToast(this, "请输您的密码");
            return false;
        }
        if (pass.length() > 15) {
            UiUtil.showLongToast(this, "密码长度必须小于15个字符");
            return false;
        }
        String passcon = editPassConfirm.getText().toString();
        if (TextUtils.isEmpty(passcon)) {
            UiUtil.showLongToast(this, "请输入确认您的密码");
            return false;
        }
        if (!passcon.equals(pass)) {
            UiUtil.showLongToast(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        setTitle("注册");
    }


    @OnClick({R.id.get_code_bt, R.id.next_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                if (checkInputFirst()) {
                    sendCodeDao.sendCode(phone, SendCodeDao.REGIST_TYPE);
                    showProgress(true);
                }
                break;
            case R.id.next_bt:

                if (checkInputFirst()) {
                    if (TextUtils.isEmpty(eidtCode.getText().toString())) {
                        UiUtil.showLongToast(getApplicationContext(), "请填写验证码");
                    } else {
                        registerDao.register(phone, pass, eidtCode.getText().toString(), userName);
                        showProgress(true);
                    }
                }
                break;
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.SEND_CODE) {
            getCodeBt.startNumCode();
            UiUtil.showLongToast(this, "获取验证码成功");
        } else if (requestCode == RequestCode.REGISTER) {
            UserInfo userInfo = registerDao.getUserInfo();
            if (userInfo != null) {
                AppUtil.setUserId(this, userInfo.getId());
                UiUtil.showLongToast(this, "注册成功");
                transUi(CompleteUserInfoActivity.class, null);
                finish();
            }
        }

    }
}
