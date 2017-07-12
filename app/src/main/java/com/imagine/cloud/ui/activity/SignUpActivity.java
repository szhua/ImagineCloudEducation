package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/*报名界面*/
public class SignUpActivity extends BaseActivity {

    @InjectView(R.id.next_bt)
    TextView nextBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(SelectPayTypeActivity.class,null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("报名");
    }
}
