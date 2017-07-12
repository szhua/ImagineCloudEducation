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
import com.runer.liabary.tab.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FragmentInfoProject
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setAdapter(new CacheFragmentAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewpager);
    }

    //教育部科研项目、国家社科基金项目、国家自科基金项目、其他项目
    private class CacheFragmentAdapter extends FragmentPagerAdapter {

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
            return new HomeFragment();
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
