package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.ui.fragment.HomeFragment;
import com.imagine.cloud.widget.adviewpager.AdViewPager;
import com.imagine.cloud.widget.adviewpager.BannerBean;
import com.runer.liabary.tab.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExampleAcitivty extends AppCompatActivity {


    @InjectView(R.id.app_bar)
    AppBarLayout appBar;
    @InjectView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.banner)
    AdViewPager banner;
    private String[] titles = new String[]{"会议资讯", "项目资讯"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_acitivty);
        ButterKnife.inject(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager()));
        tabLayout.setViewPager(viewPager);
        List<BannerBean> bannerBeanList =new ArrayList<>();
     for(int i=0;i<3;i++){
         BannerBean bannerBean =new BannerBean() ;
         bannerBeanList.add(bannerBean) ;

     }
     banner.setBannerEntities(bannerBeanList);

    }

    class ViewpagerAdapter extends BaseFragmentPagerAdapter {

        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new HomeFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
