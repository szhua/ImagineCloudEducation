package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {


    @InjectView(R.id.edit_advice)
    EditText editAdvice;
    @InjectView(R.id.commit_bt)
    TextView commitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.inject(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("意见建议");
    }

    @OnClick(R.id.commit_bt)
    public void onViewClicked() {
    }
}
