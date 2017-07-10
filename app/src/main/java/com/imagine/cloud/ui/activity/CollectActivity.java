package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.ui.fragment.CollectFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CollectActivity extends BaseActivity {


    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    private String[] titles = new String[]{"收藏的会议", "收藏的项目"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);
        viewpager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager()));
      //  tabLayout.setViewPager(viewpager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("我的收藏");
    }

    class ViewpagerAdapter extends BaseFragmentPagerAdapter {

        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CollectFragment();
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
