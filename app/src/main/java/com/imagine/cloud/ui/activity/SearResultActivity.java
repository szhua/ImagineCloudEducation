package com.imagine.cloud.ui.activity;
import android.os.Bundle;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.ui.fragment.HomeFragment;

public class SearResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sear_result);
        addFragmentList(R.id.container , new HomeFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("搜索结果");
    }
}
