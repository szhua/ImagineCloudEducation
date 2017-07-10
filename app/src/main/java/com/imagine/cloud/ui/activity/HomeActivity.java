package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.ui.fragment.CourseFragment;
import com.imagine.cloud.ui.fragment.HomeFragment;
import com.imagine.cloud.ui.fragment.InforMationFragment;
import com.imagine.cloud.ui.fragment.MeetingFragment;
import com.imagine.cloud.ui.fragment.MineFragment;
import com.runer.liabary.widget.TabFragmentHost;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @InjectView(android.R.id.tabhost)
    TabFragmentHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        //registerMessageListener();
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setBackgroundResource(R.color.white);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.getTabWidget().setBackgroundResource(R.color.white);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(getTabItemView(R.drawable.home_infor_selector, "资讯")),
                InforMationFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("meeting").setIndicator(getTabItemView(R.drawable.home_meetion_selector, "会议")),
                MeetingFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("course").setIndicator(getTabItemView(R.drawable.home_course_selector, "课程")),
                CourseFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("mine").setIndicator(getTabItemView(R.drawable.home_mine_selector, "我的")),
                MineFragment.class, null);
        mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (int) getResources().getDimension(R.dimen.tab_height);
        mTabHost.setOnTabChangedListener(this);



    }
    @Override
    public void onTabChanged(String tabId) {
    }
    /*create tabLayout from drawable and title*/
    private View getTabItemView(int id, String title) {
        View view = getLayoutInflater().inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(id);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(title);
        return view;
    }
}
