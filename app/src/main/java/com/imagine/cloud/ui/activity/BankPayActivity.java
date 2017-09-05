package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.AddOrderResultBean;
import com.imagine.cloud.bean.BankInfoBean;
import com.imagine.cloud.dao.GetUserBankDao;
import com.imagine.cloud.dao.PayDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.CodeButton;
import com.imagine.cloud.widget.NormalInputEditText;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BankPayActivity extends BaseActivity {

    @InjectView(R.id.card_num)
    NormalInputEditText cardNumInput;
    @InjectView(R.id.name)
    NormalInputEditText nameInput;
    @InjectView(R.id.id_number)
    NormalInputEditText idNumberInput;
    @InjectView(R.id.phone_num)
    NormalInputEditText phoneNumInput;
    @InjectView(R.id.period_of_validity)
    NormalInputEditText periodOfValidityInput;
    @InjectView(R.id.cvv_num)
    NormalInputEditText cvvNumInput;
    @InjectView(R.id.eidt_code)
    EditText eidtCodeInptu;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.commit_to_pay)
    TextView commitToPay;
    @InjectView(R.id.debit_card_radio)
    RadioButton debitCardRadio;
    @InjectView(R.id.credit_card_radio)
    RadioButton creditCardRadio;
    @InjectView(R.id.card_container)
    RadioGroup cardContainer;

    private final static int DEBIT_CARD_TYPE = 0;
    private final static int CREDIT_CARD_TYPE = 1;
    private int payType;
    private PayDao payDao;
    private String orderNo;
    private AddOrderResultBean addOrderResultBean ;
    private GetUserBankDao getUserBankDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_pay);
        ButterKnife.inject(this);


        getUserBankDao =new GetUserBankDao(this,this) ;
        getUserBankDao.getUserBank(AppUtil.getUserId(this));
        
        addOrderResultBean = (AddOrderResultBean) getIntent().getExtras().get("data");

        payDao = new PayDao(this, this);
        debitCardRadio.setChecked(true);

        cardContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.debit_card_radio) {
                    payType = DEBIT_CARD_TYPE;
                } else {
                    payType = CREDIT_CARD_TYPE;
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("银行卡支付");
    }



    String cardNum;
    String name;
    String IDnumber;
    String phoneNum;
    String periodOfValidity;
    String cvvNum;
    String identifyingCode;



    //验证输入的内容
    private boolean checkInputNum() {

        cardNum = cardNumInput.getInputContent();
        name = nameInput.getInputContent();
        IDnumber = idNumberInput.getInputContent();
        phoneNum = phoneNumInput.getInputContent();
        periodOfValidity =periodOfValidityInput.getInputContent();
        cvvNum =cvvNumInput.getInputContent() ;

        if (TextUtils.isEmpty(cardNum)) {
            UiUtil.showLongToast(this, "请输入您的银行卡号");
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            UiUtil.showLongToast(this, "请输入您的姓名");
            return false;
        }
        if (TextUtils.isEmpty(IDnumber)) {
            UiUtil.showLongToast(this, "请输入您的身份证号");
            return false;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            UiUtil.showLongToast(this, "请输入您的手机号");
            return false;
        }
        if (!UiUtil.isValidMobileNo(phoneNum)) {
            UiUtil.showLongToast(this, "填写的电话格式不正确");
            return false;
        }
        //信用卡支付的情况下
        if (payType == CREDIT_CARD_TYPE) {
            if (TextUtils.isEmpty(periodOfValidity)) {
                UiUtil.showLongToast(this, "有效期为空");
                return false;
            }
            if (TextUtils.isEmpty(cvvNum)) {
                UiUtil.showLongToast(this, "CVV码为空");
                return false;
            }
            if (cvvNum.length() != 3) {
                UiUtil.showLongToast(this, "请填写CVV码的后三位");
                return false;
            }
        }else{
            cvvNum ="" ;
            periodOfValidity ="" ;
        }
        return true;
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.BANK_PAY_GET_CODE) {
            UiUtil.showLongToast(this, "短信验证码已发送");
            getCodeBt.startNumCode();
            orderNo = payDao.getMer_order_no();
        }else if (requestCode == RequestCode.BANK_PAY) {
            UiUtil.showLongToast(this, "支付成功！");
            Bundle bundle =new Bundle() ;
            bundle.putSerializable("data",addOrderResultBean);
            bundle.putString("type","bank");
            transUi(PaySuccessActivity.class,bundle);
            finish();
        }else if(requestCode==RequestCode.GET_USER_BANK){
            BankInfoBean bankBean = getUserBankDao.getBankInfoBean();
            if(bankBean!=null){
                cardNumInput.setRightText(bankBean.getBank_no());
                nameInput.setRightText(bankBean.getName());
                idNumberInput.setRightText(bankBean.getId_card());
                phoneNumInput.setRightText(bankBean.getMobile());
                if(TextUtils.isEmpty(bankBean.getCvv2())){
                    debitCardRadio.setChecked(true);
                }else{
                    creditCardRadio.setChecked(true);
                    periodOfValidityInput.setRightText(bankBean.getExpired_date());
                    cvvNumInput.setRightText(bankBean.getCvv2());
                }
            }
        }
    }
    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        super.onRequestError(requestCode, errorInfo, error_code);
        if(error_code==1003){
            finish();
        }
    }


    @OnClick({R.id.get_code_bt, R.id.commit_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                if (checkInputNum()) {
                    payDao.bankPayGetCode(addOrderResultBean.getId(), name, cardNum, IDnumber, phoneNum,periodOfValidity,cvvNum);
                    showProgressWithMsg(true, "正在获取验证码");
                }
                break;
            case R.id.commit_to_pay:
                if (checkInputNum()) {
                    identifyingCode = eidtCodeInptu.getText().toString();
                    if (TextUtils.isEmpty(identifyingCode)) {
                        UiUtil.showLongToast(this, "请输入验证码");
                        return;
                    }
                    if (TextUtils.isEmpty(orderNo)) {
                        UiUtil.showLongToast(this, "请填写正确的验证码");
                        return;
                    }
                    payDao.bankPay(identifyingCode, orderNo);
                    showProgressWithMsg(true, "正在支付");
                }
                break;
        }
    }
}
