package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.HomeListAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.MeetingProjectListBean;
import com.imagine.cloud.dao.MeetingProjectSearchDao;
import com.imagine.cloud.widget.LoamoreView;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/*搜索结果界面*/
public class SearResultActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private MeetingProjectSearchDao meetingProjectSearchDao;
    String searchKey;
    private List<MeetingProjectListBean> meetingProjectListBeen;
    private HomeListAdapter  meetingProAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sear_result);
        ButterKnife.inject(this);
        searchKey = getStringExtras("key", "");
        meetingProAdapter = new HomeListAdapter(meetingProjectListBeen);
        meetingProAdapter.setOnLoadMoreListener(this,recyclerView);
        //跳转；；；；；
        meetingProAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
              try{  MeetingProjectListBean meetingBean = meetingProjectListBeen.get(position);
                      if (meetingBean!=null){
                          Bundle bundle =new Bundle();
                          bundle.putString("id",meetingBean.getId());
                          if("0".equals(meetingBean.getInfo_type())){
                              transUi(MeetingDetailActivity.class,bundle);
                          }else{
                              transUi(ProjectDetailActivity.class,bundle);
                          }
                          finish();
                      }
              }catch (Exception e){e.printStackTrace();}
            }
        });

        meetingProAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        meetingProAdapter.setLoadMoreView(new LoamoreView());

        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();

        swiperefresh.setColorSchemeColors(getRefreshColor(this));
        swiperefresh.setOnRefreshListener(this);
        RunerLinearManager linearLayoutManager = new RunerLinearManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meetingProAdapter);
        meetingProjectSearchDao = new MeetingProjectSearchDao(this, this);
        meetingProjectSearchDao.search(searchKey);
        showProgress(true);


    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            meetingProjectListBeen =  meetingProjectSearchDao.getMeetingProjectListBeen();
            meetingProAdapter.setNewData(meetingProjectListBeen);
            //加载完成
            meetingProAdapter.loadMoreEnd();
            //设置为空数据显示
            if(meetingProjectListBeen==null||meetingProjectListBeen.isEmpty()){
                meetingProAdapter.setEmptyView(getEmptyView("无搜索结果"));
            }
        }
    }
    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        meetingProAdapter.loadMoreEnd();
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("搜索结果");
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        meetingProjectSearchDao.search(searchKey);
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        if(swiperefresh!=null)
        swiperefresh.setRefreshing(false);
    }

}
