package com.imagine.cloud.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.UserCenterItemAdapter;
import com.imagine.cloud.base.BaseFragment;
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.bean.MineListItemBean;
import com.imagine.cloud.bean.UserInfo;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.ui.activity.ChangePassActivity;
import com.imagine.cloud.ui.activity.CollectActivity;
import com.imagine.cloud.ui.activity.FeedBackActivity;
import com.imagine.cloud.ui.activity.LoginActivity;
import com.imagine.cloud.ui.activity.MessageActivity;
import com.imagine.cloud.ui.activity.MyMeetingActivity;
import com.imagine.cloud.ui.activity.UserInfoActivity;
import com.imagine.cloud.util.AppUtil;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.DataCleanManager;
import com.runer.liabary.util.UiUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by szhua on 2017/7/4/004.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MineFragment
 * 个人中心界面
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.item_list)
    RecyclerView itemList;
    @InjectView(R.id.exist_bt)
    TextView existBt;

    private CircleImageView headerImag;
    private TextView userNameTv;
    private View headerContainer;
    private UserCenterItemAdapter  userCenterItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        //设置个人信息
        UserInfo userInfo = AppUtil.getUserInfo(getContext()) ;
        if(userInfo!=null){
            if(headerImag!=null)
            Picasso.with(getContext()).load(Requst.BASE_IMG_URL+ userInfo.getHead()).placeholder(R.drawable.loading_1_1).into(headerImag);
            if(userNameTv!=null)
            if(!TextUtils.isEmpty(userInfo.getUser_name()))
            userNameTv.setText(userInfo.getUser_name());
            else
             userNameTv.setText("暂无昵称");
        }
        //设置缓存大小
        if(userCenterItemAdapter!=null){
            try {
                userCenterItemAdapter.getItem(4).setRightText(DataCleanManager.getTotalCacheSize(getContext()));
                userCenterItemAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //动态添加列表项；
        ArrayList<MineListItemBean> mineListItemBeens = new ArrayList<>();
        mineListItemBeens.add(new MineListItemBean("我的消息", "", R.drawable.mine_msg));
        mineListItemBeens.add(new MineListItemBean("我的会议", "", R.drawable.mine_meeting));
        mineListItemBeens.add(new MineListItemBean("我的收藏", "", R.drawable.mine_collect));
        mineListItemBeens.add(new MineListItemBean("修改密码", "", R.drawable.mine_pass));
        mineListItemBeens.add(new MineListItemBean("清除缓存", "0Kb", R.drawable.mine_clean));
        mineListItemBeens.add(new MineListItemBean("关于我们", "", R.drawable.mine_about_us));
        mineListItemBeens.add(new MineListItemBean("常见问题", "", R.drawable.mine_usal_question));
        mineListItemBeens.add(new MineListItemBean("意见建议", "", R.drawable.mine_feed_back));
        mineListItemBeens.add(new MineListItemBean("版本更新", "当前版本1.0", R.drawable.mine_update_version));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
         userCenterItemAdapter = new UserCenterItemAdapter(mineListItemBeens);
        //添加点击事件
        userCenterItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                Bundle bundle;
                switch (position) {
                    case 0:
                        transUi(MessageActivity.class, null);
                        break;
                    case 1:
                        transUi(MyMeetingActivity.class, null);
                        break;
                    case 2:
                        transUi(CollectActivity.class, null);
                        break;
                    case 3:
                        transUi(ChangePassActivity.class, null);
                        break;
                    case 4:
                        //清除缓存
                        Flowable.just(getContext())
                                .doOnNext(new Consumer<Context>() {
                                    @Override
                                    public void accept(Context context) throws Exception {
                                        DataCleanManager.clearAllCache(context);
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Object>() {
                                    @Override
                                    public void accept(Object s) throws Exception {
                                        UiUtil.showLongToast(getContext(), "清除缓存成功");
                                        userCenterItemAdapter.getItem(position).setRightText(DataCleanManager.getTotalCacheSize(getContext()));
                                        userCenterItemAdapter.notifyDataSetChanged();
                                    }
                                });

                        break;
                    case 5:
                        bundle = new Bundle();
                        bundle.putString(BaseWebAcitivity.WEB_TITLE, "关于我们");
                        transUi(BaseWebAcitivity.class, bundle);
                        break;
                    case 6:
                        bundle = new Bundle();
                        bundle.putString(BaseWebAcitivity.WEB_TITLE, "常见问题");
                        transUi(BaseWebAcitivity.class, bundle);
                        break;
                    case 7:
                        transUi(FeedBackActivity.class,null);
                        break;
                    case 8:
                        UiUtil.showLongToast(getContext(),getString(R.string.not_open));
                        break;
                }
            }
        });


        userCenterItemAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.mine_header_layout, null);
        headerImag = (CircleImageView) headView.findViewById(R.id.header);
        userNameTv = (TextView) headView.findViewById(R.id.username);
        headerContainer = headView.findViewById(R.id.header_container);
        userCenterItemAdapter.addHeaderView(headView);

        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext()).type(0, R.drawable.item_decoration_shape).create();
        itemList.setLayoutManager(linearLayoutManager);
        itemList.addItemDecoration(decoration);
        itemList.setHasFixedSize(true);
        itemList.setAdapter(userCenterItemAdapter);

        existBt.setOnClickListener(this);
        headerContainer.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View v) {
if(v==headerContainer){
    transUi(UserInfoActivity.class,null);
    //退出登录
}else if(v==existBt){
    transUi(LoginActivity.class,null);
}
    }
}
