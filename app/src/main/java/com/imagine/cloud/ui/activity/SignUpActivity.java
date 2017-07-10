package com.imagine.cloud.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("报名");
    }
}
