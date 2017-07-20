package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.dao.EidtPassDao;
import com.imagine.cloud.dao.SendCodeDao;
import com.imagine.cloud.widget.CodeButton;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/*修改密码界面*/
public class ChangePassActivity extends BaseActivity {

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
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.edit_pass)
    EditText editPass;
    @InjectView(R.id.edit_pass_confirm)
    EditText editPassConfirm;
    @InjectView(R.id.finish_bt)
    TextView finishBt;

    private EidtPassDao eidtPassDao ;
    private SendCodeDao sendCodeDao ;
    private String phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.inject(this);
        eidtPassDao =new EidtPassDao(this,this);
        sendCodeDao =new SendCodeDao(this,this) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("修改密码");
    }



    private String pass ;
    @OnClick({R.id.get_code_bt, R.id.finish_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:

               phone =editPhoneNum.getText().toString() ;
                if(TextUtils.isEmpty(phone)){
                    UiUtil.showLongToast(this,"手机号为空");
                    return;
                }
                if(!UiUtil.isValidMobileNo(phone)){
                    UiUtil.showLongToast(this,"手机号格式不正确");
                    return;
                }
                sendCodeDao.sendCode(phone,SendCodeDao.CHANGE_PASS_TYPE);
                showProgressWithMsg(true,"正在获取验证码");

                break;

            case R.id.finish_bt:
                pass =editPass.getText().toString() ;
                if(TextUtils.isEmpty(pass)){
                    UiUtil.showLongToast(this,"输入的密码为空");
                    return;
                }
                String passRe =editPassConfirm.getText().toString() ;
                if(TextUtils.isEmpty(passRe)){
                    UiUtil.showLongToast(this,"请在此确认密码");
                    return;
                }
                if(!passRe.equals(pass)){
                    UiUtil.showLongToast(this,"两次输入的密码不一致");
                    return;
                }
                String code =eidtCode.getText().toString() ;
                if(TextUtils.isEmpty(code)){
                    UiUtil.showLongToast(this,"输入的验证码为空");
                    return;
                }
                eidtPassDao.eidtPass(phone,code,pass);
                showProgressWithMsg(true,"正在修改密码");

                break;
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.SEND_CODE){
            UiUtil.showLongToast(this,"获取验证码成功");
            getCodeBt.startNumCode();
        }else if(requestCode==RequestCode.CHANGE_PASS){
            UiUtil.showLongToast(this,"修改密码成功");
            finish();
        }
    }
}
