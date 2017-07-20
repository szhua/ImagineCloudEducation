package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.AddOrderBean;
import com.imagine.cloud.dao.AddOrderDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.NormalInputEditText;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*报名界面*/
public class SignUpActivity extends BaseActivity {

    @InjectView(R.id.next_bt)
    TextView nextBt;
    @InjectView(R.id.meeting_title)
    TextView meetingTitle;
    @InjectView(R.id.user_name)
    NormalInputEditText userNameInput;
    @InjectView(R.id.user_phone)
    NormalInputEditText userPhoneInput;
    @InjectView(R.id.user_email)
    NormalInputEditText userEmailInput;
    @InjectView(R.id.male_radio)
    RadioButton maleRadio;
    @InjectView(R.id.female_radio)
    RadioButton femaleRadio;
    @InjectView(R.id.sex_radios)
    RadioGroup sexRadios;
    @InjectView(R.id.nation_name)
    NormalInputEditText nationNameInput;
    @InjectView(R.id.school_name)
    NormalInputEditText schoolNameInput;
    @InjectView(R.id.single_stay_radio)
    RadioButton singleStayRadio;
    @InjectView(R.id.multi_stay_radio)
    RadioButton multiStayRadio;
    @InjectView(R.id.stay_radios)
    RadioGroup stayRadios;
    @InjectView(R.id.invoice_title)
    NormalInputEditText invoiceTitleInput;
    @InjectView(R.id.reg_number)
    NormalInputEditText regNumberInput;
    @InjectView(R.id.school_adress)
    NormalInputEditText schoolAdressInput;
    @InjectView(R.id.bank_name)
    NormalInputEditText bankNameInput;
    @InjectView(R.id.bank_account)
    NormalInputEditText bankAccountInput;
    @InjectView(R.id.others)
    EditText othersInput;

    String id ;
    private AddOrderDao addOrderDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        addOrderDao =new AddOrderDao(this,this) ;


        id =getStringExtras("id","") ;

        Logger.d(id);


        maleRadio.setChecked(true);
        singleStayRadio.setChecked(true);


        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    AddOrderBean addOrderBean =new AddOrderBean() ;
                    addOrderBean.setId(id);
                    addOrderBean.setAddress(schollAddress);
                    addOrderBean.setBank(bankName);
                    addOrderBean.setBank_num(bankAccount);
                    addOrderBean.setEmail(userEmail);
                    addOrderBean.setMobile(userPhone);
                    addOrderBean.setHeader(invoceTitle);
                    addOrderBean.setOther(othersInfo);
                    addOrderBean.setNation(nationName);
                    addOrderBean.setUser_id(AppUtil.getUserId(SignUpActivity.this));
                    addOrderBean.setNum(regNum);
                    if(femaleRadio.isChecked()){
                     addOrderBean.setSex("1");
                    }else{
                        addOrderBean.setSex("0");
                    }
                    if(singleStayRadio.isChecked()){
                        addOrderBean.setLiving("0");
                    }else{
                        addOrderBean.setLiving("1");
                    }
                    addOrderDao.addOrder(addOrderBean);
                    showProgress(true);
                }
            }
        });


    }


    //用户名
    String userName ;
    //用户的手机
    String userPhone ;
    //用户的电子邮件
    String userEmail;
    //名族
    String nationName ;
    //学校名称
    String schoolName ;
    //发票抬头
    String invoceTitle;
    //纳税人识别号
    String regNum ;
    //学校地址
    String schollAddress ;
    //开户行名称
    String bankName ;
    //开户行账号
    String bankAccount ;
    //其他的信息
    String othersInfo ;

    //验证输入内容
    private boolean checkInput(){

        userName =userNameInput.getInputContent();
        userPhone=userPhoneInput.getInputContent() ;
        userEmail=userEmailInput.getInputContent() ;
        nationName=nationNameInput.getInputContent();
        schoolName=schoolNameInput.getInputContent() ;
        invoceTitle =invoiceTitleInput.getInputContent() ;
        regNum =regNumberInput.getInputContent() ;
        schollAddress =schoolAdressInput.getInputContent() ;
        bankName  =bankNameInput.getInputContent();
        bankAccount =bankAccountInput.getInputContent() ;
        othersInfo =othersInput.getText().toString() ;

        if(TextUtils.isEmpty(userName)){
            UiUtil.showLongToast(this,"用户名为空");
            return false ;
        }
        if(TextUtils.isEmpty(userPhone)){
            UiUtil.showLongToast(this,"手机号为空");
            return false ;
        }
        if(!UiUtil.isValidMobileNo(userPhone)){
            UiUtil.showLongToast(this,"手机号格式不正确");
            return  false ;
        }
        if(TextUtils.isEmpty(userEmail)){
            UiUtil.showLongToast(this,"邮箱为空");
            return false ;
        }
        if(!UiUtil.checkEmail(userEmail)){
            UiUtil.showLongToast(this,"邮箱格式不正确");
            return  false ;
        }
        if(TextUtils.isEmpty(nationName)){
            UiUtil.showLongToast(this,"名族为空");
            return false ;
        }
        if(TextUtils.isEmpty(schoolName)){
            UiUtil.showLongToast(this,"学校名称为空");
            return false ;
        }
        if(TextUtils.isEmpty(invoceTitle)){
            UiUtil.showLongToast(this,"未填写发票抬头");
            return false ;
        }
        if(TextUtils.isEmpty(regNum)){
            UiUtil.showLongToast(this,"未填写纳税人识别号");
            return false ;
        }
        if(TextUtils.isEmpty(schollAddress)){
            UiUtil.showLongToast(this,"学校地址为空");
            return false ;
        }
        if(TextUtils.isEmpty(bankName)){
            UiUtil.showLongToast(this,"开户行为空");
            return false ;
        }
        if(TextUtils.isEmpty(bankAccount)){
            UiUtil.showLongToast(this,"开户行账号为空");
            return false ;
        }
        return   true ;
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            String orderId =addOrderDao.getOrderId() ;
            if(!TextUtils.isEmpty(orderId)){
                UiUtil.showLongToast(this,"下订单成功！");
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("报名");
    }
}
