package com.imagine.cloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.base.BaseFragmentPagerAdapter;
import com.imagine.cloud.dao.MeetingProjectListDao;
import com.runer.liabary.tab.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FragmentInfoProject
 * 项目资讯
 */

public class FragmentInfoProject extends BaseFragment {

    @InjectView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_project_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private List<FragmentProList> fragmentProListList ;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentProListList =new ArrayList<>() ;

        fragmentProListList.add(FragmentProList.getInstance(MeetingProjectListDao.EDU_SEARCH_TYPE));
        fragmentProListList.add(FragmentProList.getInstance(MeetingProjectListDao.SCO_SEARCH_TYPE));
        fragmentProListList.add(FragmentProList.getInstance(MeetingProjectListDao.NATURE_SEARCH_TYPE));
        fragmentProListList.add(FragmentProList.getInstance(MeetingProjectListDao.OTHER_SEARCH_TYPE));

        viewpager.setAdapter(new CacheFragmentAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewpager);
    }

    //教育部科研项目、国家社科基金项目、国家自科基金项目、其他项目
    private class CacheFragmentAdapter extends BaseFragmentPagerAdapter {

        String[] titles = new String[]{"教育部科研项目", "国家社科基金项目", "国家自科基金项目", "其他项目"};

        public CacheFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentProListList.get(position);
        }
        @Override
        public int getCount() {
            return titles.length;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
