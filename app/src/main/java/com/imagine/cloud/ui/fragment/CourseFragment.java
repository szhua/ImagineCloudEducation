package com.imagine.cloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.CourseAdapter;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.bean.CourseBean;
import com.imagine.cloud.dao.CourseDao;
import com.imagine.cloud.ui.activity.CourseDetailActivity;
import com.imagine.cloud.ui.activity.ExampleAcitivty;
import com.imagine.cloud.ui.activity.LoginActivity;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseFragment
 */

public class CourseFragment extends BaseFragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private CourseAdapter  courseAdapter;

    private CourseDao courseDao ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RunerLinearManager linearLayoutManager = new RunerLinearManager(getContext());
        courseAdapter = new CourseAdapter(datas);
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
             Bundle bundle =new Bundle() ;
             bundle.putString("id",courseAdapter.getItem(position).getId());
             transUi(CourseDetailActivity.class,bundle);
            }
        });
        courseAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        courseAdapter.setLoadMoreView(new LoamoreView());
        courseAdapter.setOnLoadMoreListener(this);
        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        swiperefresh.setColorSchemeColors(getRefreshColor(getContext()));
        swiperefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(courseAdapter);
        courseDao =new CourseDao(getContext(),this) ;
        courseDao.refresh();
    }


    private List<CourseBean> datas ;


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_1){
              datas =courseDao.getDatas() ;
              courseAdapter.setNewData(datas);
              if(datas==null||datas.isEmpty()){
                  courseAdapter.setEmptyView(getEmptyView());
              }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        courseDao.refresh();
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        swiperefresh.setRefreshing(false);
        courseAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if(courseDao.hasMore()){
            courseDao.nextPage();
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    courseAdapter.loadMoreEnd();
                }
            },1500);
        }
    }
}
