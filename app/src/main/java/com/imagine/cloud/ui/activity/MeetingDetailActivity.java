package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import com.imagine.cloud.dao.MessageDao;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.widget.StateTextView;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/*会议详情*/
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
    @InjectView(R.id.buy_num)
    TextView buyNum;

    private MeetingInfoDao meetingInfoDao;
    private MeetingDetailBean meetingDetailBean;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.inject(this);
        id = getStringExtras("id", "");
        meetingInfoDao = new MeetingInfoDao(this, this);
        meetingInfoDao.getInfo(AppUtil.getUserId(this), id);
        //若是从消息中过来的时候
        String type = getStringExtras("type", "0");
        if ("msg".equals(type)) {
            MessageDao messageDao = new MessageDao(this, this);
            String msgId = getStringExtras("msg_id", "");
            messageDao.setMsgRead(msgId);
        }
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
            buyNum.setText(meetingDetailBean.getBuy_num());
            //判断报名的类型，是否可以报名
            if("0".equals(meetingDetailBean.getType())){
                baomingContainer.setEnabled(true);
                baomingContainer.setVisibility(View.VISIBLE);
            }else if("1".equals(meetingDetailBean.getType())){
                baomingContainer.setVisibility(View.GONE);
            }else if("2".equals(meetingDetailBean.getType())){
                buyNum.setText("已过期");
                buyNum.setTextColor(ContextCompat.getColor(this,R.color.text_color_light));
                baomingContainer.setEnabled(false);
            }
            //加载网页
            if (!TextUtils.isEmpty(meetingDetailBean.getUrl()))
                addFragmentList(R.id.container, BaseWebFragment.getInstance(meetingDetailBean.getUrl()));
        } else if (requestCode == RequestCode.ADD_ZAN) {
            UiUtil.showLongToast(this, "点赞成功");
            zanIcon.setSelected(true);
            zanIcon.setText(String.valueOf(Integer.parseInt(meetingDetailBean.getLike_num()) + 1));
            //增加收藏
        } else if (requestCode == RequestCode.ADD_FAV) {
            UiUtil.showLongToast(this, "收藏成功");
            meetingDetailBean.setIsfav(1);
            collectIcon.setSelected(true);
            meetingDetailBean.setFav_num(String.valueOf(Integer.parseInt(meetingDetailBean.getFav_num()) + 1));
            collectIcon.setText(meetingDetailBean.getFav_num());
            //取消收藏
        } else if (requestCode == RequestCode.DEL_FAV) {
            UiUtil.showLongToast(this, "取消成功");
            meetingDetailBean.setIsfav(0);
            collectIcon.setSelected(false);
            meetingDetailBean.setFav_num(String.valueOf(Integer.parseInt(meetingDetailBean.getFav_num()) - 1));
            collectIcon.setText(meetingDetailBean.getFav_num());
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
                if (AppUtil.chckeLogin(this, true)) {
                    meetingInfoDao.addLike(AppUtil.getUserId(this), id);
                    showProgress(true);
                }
                break;
            case R.id.baoming_container:
                if(AppUtil.chckeLogin(this,true)){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    transUi(SignUpActivity.class, bundle);
                }
                break;
            case R.id.collect_container:
                if (AppUtil.chckeLogin(this, true)) {
                    if (meetingDetailBean.getIsfav() == 0) {
                        meetingInfoDao.addFav(AppUtil.getUserId(this), id);
                        showProgress(true);
                    } else {
                        meetingInfoDao.delFav(AppUtil.getUserId(this), id);
                        showProgress(true);
                    }
                }
                break;
        }
    }
}
