package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebFragment;

public class ProjectDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        addFragmentList(R.id.container, BaseWebFragment.getInstance("https://github.com/szhua"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("项目详情");
    }
}
