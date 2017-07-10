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
import com.imagine.cloud.adapter.HomeListAdapter;
import com.imagine.cloud.adapter.MeetingAdapter;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.dao.MeetingListDao;
import com.imagine.cloud.ui.activity.ExampleAcitivty;
import com.imagine.cloud.ui.activity.LoginActivity;
import com.imagine.cloud.ui.activity.MeetingDetailActivity;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
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
 * MeetingFragment
 * 会议界面
 */

public class MeetingFragment extends BaseFragment  {
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private MeetingAdapter meetingAdapter ;

    private MeetingListDao meetingListDao ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        meetingAdapter = new MeetingAdapter(meetingBeanList);
        meetingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",meetingBeanList.get(position).getId());
                transUi(MeetingDetailActivity.class,bundle);
            }
        });

        meetingAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        meetingAdapter.setLoadMoreView(new LoamoreView());
        meetingAdapter.setOnLoadMoreListener(this);
        //分割线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        swiperefresh.setColorSchemeColors(getColor(android.R.color.holo_orange_dark),getColor(R.color.albumColorPrimary),getColor(android.R.color.holo_red_light));
        swiperefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meetingAdapter);

        meetingListDao = new MeetingListDao(getContext() ,this) ;
        meetingListDao.refresh();
        showProgress(true);
    }


    private List<MeetingBean> meetingBeanList ;
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_1){
            meetingBeanList =meetingListDao.getDatas();
            Logger.d(meetingBeanList);
            meetingAdapter.setNewData(meetingBeanList);
            if(meetingListDao.hasMore()){

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
        meetingListDao.refresh();
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        swiperefresh.setRefreshing(false);
        meetingAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if(meetingListDao.hasMore()){
             meetingListDao.nextPage();
        }else{
           recyclerView.postDelayed(new Runnable() {
               @Override
               public void run() {
                   meetingAdapter.loadMoreEnd();
               }
           },1500);
        }

    }
}
