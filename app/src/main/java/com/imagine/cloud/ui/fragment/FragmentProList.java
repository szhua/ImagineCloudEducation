package com.imagine.cloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.HomeListAdapter;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.bean.MeetingProjectListBean;
import com.imagine.cloud.dao.MeetingProjectListDao;
import com.imagine.cloud.ui.activity.ProjectDetailActivity;
import com.imagine.cloud.widget.LoamoreView;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by szhua on 2017/7/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FragmentProList
 */
public class FragmentProList extends BaseFragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String type;

    private List<MeetingProjectListBean> datas ;

    private MeetingProjectListDao meetingProjectListDao ;
    private HomeListAdapter  homeListAdapter;

    public static FragmentProList getInstance(String type) {
        FragmentProList fragmentProList = new FragmentProList();
        fragmentProList.type = type;
        return fragmentProList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        swiperefresh.setColorSchemeColors(getRefreshColor(getContext()));
        swiperefresh.setOnRefreshListener(this);
        RunerLinearManager linearLayoutManager = new RunerLinearManager(getContext());
        homeListAdapter = new HomeListAdapter(datas);
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",((MeetingProjectListBean)adapter.getItem(position)).getId());
                transUi(ProjectDetailActivity.class,bundle);
            }
        });
        homeListAdapter.setLoadMoreView(new LoamoreView());
        homeListAdapter.setOnLoadMoreListener(this, recyclerView);
        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeListAdapter);

        meetingProjectListDao =new MeetingProjectListDao(getContext(),this) ;
        meetingProjectListDao.refresh(type,"");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            datas =meetingProjectListDao.getData() ;
            homeListAdapter.setNewData(datas);

            if(datas==null||datas.isEmpty()){
                homeListAdapter.setEmptyView(getEmptyViewFixSize("暂无项目资讯"));
            }

        }
    }
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        swiperefresh.setRefreshing(false);
        homeListAdapter.loadMoreComplete();
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        meetingProjectListDao.refresh(type,"");
    }
    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if (meetingProjectListDao.hasMore()) {
            meetingProjectListDao.nextPage(type,"");
        } else {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    homeListAdapter.loadMoreEnd();
                }
            }, 1500);
        }
    }
}
