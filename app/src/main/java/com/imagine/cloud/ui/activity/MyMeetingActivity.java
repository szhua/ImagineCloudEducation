package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.MyMeetingAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.MeetingOrderBean;
import com.imagine.cloud.dao.MyMeetingDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyMeetingActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private MyMeetingAdapter meetingAdapter;
    private MyMeetingDao myMeetingDao ;
    private List<MeetingOrderBean>  meetingOrderBeanList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meeting);
        ButterKnife.inject(this);

        myMeetingDao =new MyMeetingDao(this,this) ;
        myMeetingDao.refresh(AppUtil.getUserId(this));

        RunerLinearManager linearLayoutManager = new RunerLinearManager(this);
        meetingAdapter= new MyMeetingAdapter(meetingOrderBeanList);
        meetingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",meetingAdapter.getItem(position).getId());
                bundle.putString("order_id",meetingAdapter.getItem(position).getOrder_id());
                bundle.putSerializable("data",meetingAdapter.getItem(position));
                transUi(AboutMeetingActivity.class,bundle);
            }
        });
        meetingAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        meetingAdapter.setLoadMoreView(new LoamoreView());
        meetingAdapter.setOnLoadMoreListener(this,recyclerView);
        VerticalItemDecoration decoration = ItemDecorations.vertical(this)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp)
                .create();
        swiperefresh.setColorSchemeColors(getRefreshColor(this));
        swiperefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meetingAdapter);

    }
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();

        if(swiperefresh!=null){
            swiperefresh.setRefreshing(false);
        }
        if(meetingAdapter!=null||recyclerView!=null){
            meetingAdapter.loadMoreComplete();
        }

    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

        if(requestCode== RequestCode.CODE_1){
            meetingOrderBeanList=myMeetingDao.getDatas();
            meetingAdapter.setNewData(meetingOrderBeanList);

            if(meetingOrderBeanList==null||meetingOrderBeanList.isEmpty()){
                meetingAdapter.setEmptyView(getEmptyView("您尚未购买会议"));
            }
        }
    }
    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if(myMeetingDao.hasMore()){
            myMeetingDao.nextPage(AppUtil.getUserId(this));
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    meetingAdapter.loadMoreEnd();
                }
            },1500);
        }
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        myMeetingDao.refresh(AppUtil.getUserId(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("我的会议");
    }
}
