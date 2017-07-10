package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebFragment;
import com.imagine.cloud.bean.MeetingDetailBean;
import com.imagine.cloud.dao.MeetingInfoDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.StateTextView;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MeetingDetailActivity extends BaseActivity {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.header)
    RelativeLayout header;
    @InjectView(R.id.zan_container)
    LinearLayout zanContainer;
    @InjectView(R.id.baoming_container)
    LinearLayout baomingContainer;
    @InjectView(R.id.collect_container)
    LinearLayout collectContainer;
    @InjectView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @InjectView(R.id.container)
    LinearLayout container;
    @InjectView(R.id.zan_icon)
    StateTextView zanIcon;
    @InjectView(R.id.collect_icon)
    StateTextView collectIcon;

    private MeetingInfoDao meetingInfoDao;
    private MeetingDetailBean meetingDetailBean;
    private String  id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.inject(this);

        addFragmentList(R.id.container, BaseWebFragment.getInstance("https://github.com/szhua"));
        id = getStringExtras("id", "");
        meetingInfoDao = new MeetingInfoDao(this, this);
        meetingInfoDao.getInfo(AppUtil.getUserId(this), MeetingInfoDao.MEETING_TYPE, id);
        baomingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(SignUpActivity.class, null);
            }
        });

    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            meetingDetailBean = meetingInfoDao.getMeetingDetailBean();
            if (meetingDetailBean != null) {
                //是否点赞
                if (0 == (meetingDetailBean.getIslike())) {
                    zanIcon.setSelected(false);
                } else {
                    zanIcon.setSelected(true);
                }

                zanIcon.setText(meetingDetailBean.getLike_num());
                //是否收藏
                if (0 == (meetingDetailBean.getIsfav())) {
                    collectIcon.setSelected(false);
                } else {
                    collectIcon.setSelected(true);
                }
                collectIcon.setText(meetingDetailBean.getFav_num());
            }
        }else if(requestCode==RequestCode.ADD_ZAN){
            zanIcon.setSelected(true);
            zanIcon.setText(String.valueOf(Integer.parseInt(meetingDetailBean.getLike_num())+1));
        }else if(requestCode==RequestCode.ADD_FAV){
            collectIcon.setSelected(true);
            collectIcon.setText(String.valueOf(Integer.parseInt(meetingDetailBean.getFav_num())+1));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("会议详情");
    }

    @OnClick({R.id.zan_container, R.id.baoming_container, R.id.collect_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zan_container:
                meetingInfoDao.addLike(AppUtil.getUserId(this),id);
                showProgress(true);
                break;
            case R.id.baoming_container:
                break;
            case R.id.collect_container:
                meetingInfoDao.addFav(AppUtil.getUserId(this),id);
                showProgress(true);
                break;
        }
    }
}
