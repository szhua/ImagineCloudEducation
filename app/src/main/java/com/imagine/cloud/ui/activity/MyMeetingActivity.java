package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.HomeListAdapter;
import com.imagine.cloud.adapter.MeetingAdapter;
import com.imagine.cloud.adapter.MyMessageAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MyMeetingActivity extends BaseActivity {
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private MeetingAdapter  meetingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meeting);
        ButterKnife.inject(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         meetingAdapter= new MeetingAdapter(null);
        meetingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        meetingAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        meetingAdapter.setLoadMoreView(new LoamoreView());
        meetingAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                meetingAdapter.loadMoreEnd();
                                Logger.d(aLong);
                            }
                        });
            }
        });

        VerticalItemDecoration decoration = ItemDecorations.vertical(this)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
        swiperefresh.setColorSchemeColors(getRefreshColor(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meetingAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("我的会议");
    }
}
