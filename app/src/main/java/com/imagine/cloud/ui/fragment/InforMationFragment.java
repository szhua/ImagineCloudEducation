package com.imagine.cloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.dao.GetBannerDao;
import com.imagine.cloud.ui.activity.MessageActivity;
import com.imagine.cloud.ui.activity.SearchActivity;
import com.imagine.cloud.widget.adviewpager.AdViewPager;
import com.imagine.cloud.widget.adviewpager.BannerBean;
import com.runer.liabary.tab.SlidingTabLayout;
import com.runer.net.RequestCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * InforMationFragment
 */
//首页资讯
public class InforMationFragment extends BaseFragment {



    @InjectView(R.id.banner)
    AdViewPager banner;
    @InjectView(R.id.app_bar)
    AppBarLayout appBar;
    @InjectView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    @InjectView(R.id.search_bt)
    ImageView searchBt;
    @InjectView(R.id.msg_bt)
    ImageView msgBt;

    private String[] titles = new String[]{"会议资讯", "项目资讯"};
    private GetBannerDao getBannerDao ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(new ViewpagerAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewPager);

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(SearchActivity.class, null);
            }
        });
        msgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(MessageActivity.class,null);
            }
        });
        getBannerDao =new GetBannerDao(getContext(),this) ;
        getBannerDao.getBannerList();

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

        if(requestCode== RequestCode.GET_BANNER){
            List<BannerBean> banners = getBannerDao.getBannerBeens();
            if(banners!=null||!banners.isEmpty()){
                 banner.setBannerFirstTitlte(banners.get(0).getTitle());
            }
          banner.setBannerEntities(getBannerDao.getBannerBeens());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class ViewpagerAdapter extends BaseFragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new InfoMeetingFragment();
            }else{
                return new FragmentInfoProject();
            }
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
