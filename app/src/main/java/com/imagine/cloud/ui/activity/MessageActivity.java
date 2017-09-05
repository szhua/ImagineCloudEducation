package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.MyMessageAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.MessageBean;
import com.imagine.cloud.dao.MessageDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

//消息列表Activity；
public class MessageActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private MyMessageAdapter messageAdapter;
    private MessageDao messageDao ;
    private List<MessageBean> datas ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.inject(this);

        RunerLinearManager linearLayoutManager = new RunerLinearManager(this);
        messageAdapter = new MyMessageAdapter(datas);
        messageAdapter.setOnItemDeleteClickListener(new MyMessageAdapter.OnItemDeleteClickListener() {
            @Override
            public void onItemDelete(MessageBean item) {
              messageDao.delMessage(item.getId());
                showProgress(true);
            }
            @Override
            public void onItemClick(MessageBean item) {
                //判断类型进行跳转;
                Bundle bundle =new Bundle();
                if("0".equals(item.getType())){
                    bundle.putString("id",item.getMp_id());
                    bundle.putString("msg_id",item.getId());
                    bundle.putString("type","msg");
                 transUi(MeetingDetailActivity.class,bundle);
                }else if("1".equals(item.getType())){
                    bundle.putString("id",item.getMp_id());
                    bundle.putString("msg_id",item.getId());
                    bundle.putString("type","msg");
                    bundle.putSerializable("title",item.getTitle());
                    transUi(ProjectDetailActivity.class,bundle);
                }else if("3".equals(item.getType())){
                    bundle.putString("id",item.getId());
                    transUi(MessageDetailActivity.class,bundle);
                }
            }
        });

        swiperefresh.setOnRefreshListener(this);
        messageAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        messageAdapter.setLoadMoreView(new LoamoreView());
        messageAdapter.setOnLoadMoreListener(this,recyclerView);
        VerticalItemDecoration decoration = ItemDecorations.vertical(this)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        swiperefresh.setColorSchemeColors(getRefreshColor(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(messageDao==null){
            messageDao =new MessageDao(this,this) ;
        }
        messageDao.refresh(AppUtil.getUserId(this));
        showProgress(true);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_1){
            datas = messageDao.getDatas();
            messageAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                messageAdapter.setEmptyView(getEmptyView("您还没有收到任何消息"));
            }
        }else if(requestCode==RequestCode.CODE_3){
            messageDao.refresh(AppUtil.getUserId(this));
            showProgress(true);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        messageDao.refresh(AppUtil.getUserId(this));
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        swiperefresh.setRefreshing(false);
        messageAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();


        if(messageDao.hasMore()){
            messageDao.nextPage(AppUtil.getUserId(this));
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.loadMoreEnd();
                }
            },1500);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("我的信息");
    }
}
