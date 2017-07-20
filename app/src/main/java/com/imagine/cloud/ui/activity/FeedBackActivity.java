package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.dao.FeedBackDao;
import com.imagine.cloud.util.AppUtil;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//提交意见和反馈
public class FeedBackActivity extends BaseActivity {

    private FeedBackDao feedBackDao ;
    @InjectView(R.id.edit_advice)
    EditText editAdvice;
    @InjectView(R.id.commit_bt)
    TextView commitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.inject(this);
        feedBackDao =new FeedBackDao(this,this) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("意见建议");
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            UiUtil.showLongToast(this,"提交成功");
            finish();
        }
    }

    @OnClick(R.id.commit_bt)
    public void onViewClicked() {
    String content =editAdvice.getText().toString() ;
    if(!TextUtils.isEmpty(content)){
        feedBackDao.feedBack(AppUtil.getUserId(this),content);
        showProgressWithMsg(true,"正在提交意见");
    }else{
        UiUtil.showLongToast(this,"请输入您的意见和建议再进行提交");
        return;
    }
    }
}
