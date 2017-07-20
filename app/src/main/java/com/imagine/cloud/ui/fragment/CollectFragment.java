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
import com.imagine.cloud.adapter.CollectAdapter;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.bean.MeetingBean;
import com.imagine.cloud.dao.FavListDao;
import com.imagine.cloud.dao.MeetingInfoDao;
import com.imagine.cloud.ui.activity.MeetingDetailActivity;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CollectFragment
 * 收藏列表
 */
public class CollectFragment extends BaseFragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private CollectAdapter collectAdapter;
    private FavListDao favListDao;
    private List<MeetingBean> data;
    private MeetingInfoDao meetingInfoDao ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(favListDao==null){
            favListDao =new FavListDao(getContext(),this) ;
        }
        favListDao.refresh(AppUtil.getUserId(getContext()));
        showProgress(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        meetingInfoDao =new MeetingInfoDao(getContext(),this) ;
        collectAdapter = new CollectAdapter(data);

        //点击item和侧滑删除；
        collectAdapter.setOnItemDeleteClickListener(new CollectAdapter.OnItemDeleteClickListener() {
            @Override
            public void onItemDelete(MeetingBean item) {
                meetingInfoDao.delFav(AppUtil.getUserId(getContext()),item.getId());
                showProgress(true);
            }
            @Override
            public void onItemClick(MeetingBean item) {
                try{Bundle bundle =new Bundle() ;bundle.putString("id",item.getId());transUi(MeetingDetailActivity.class,bundle);}
                catch (Exception e){e.printStackTrace();}
            }
        });

        collectAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        collectAdapter.setLoadMoreView(new LoamoreView());
        collectAdapter.setOnLoadMoreListener(this);
        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        swiperefresh.setColorSchemeColors(getColor(android.R.color.holo_orange_dark), getColor(R.color.albumColorPrimary), getColor(android.R.color.holo_red_light));
        swiperefresh.setOnRefreshListener(this);
        RunerLinearManager linearLayoutManager =new RunerLinearManager(getContext()) ;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(collectAdapter);
        favListDao =new FavListDao(getContext(),this) ;



    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
      if(requestCode== RequestCode.CODE_0){
          data = favListDao.getDatas();
          collectAdapter.setNewData(data);

          if(data==null||data.isEmpty()){
              collectAdapter.setEmptyView(getEmptyView("暂无收藏"));
          }

      }else if(requestCode==RequestCode.DEL_FAV){
          favListDao.refresh(AppUtil.getUserId(getContext()));
      }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        swiperefresh.setRefreshing(false);
        collectAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if(favListDao.hasMore()){
            favListDao.nextPage(AppUtil.getUserId(getContext()));
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    collectAdapter.loadMoreEnd();
                }
            },1500);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        favListDao.refresh(AppUtil.getUserId(getContext()));
    }
}
