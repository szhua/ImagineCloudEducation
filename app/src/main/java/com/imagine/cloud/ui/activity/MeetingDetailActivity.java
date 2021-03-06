package com.imagine.cloud.ui.activity;

import android.content.Intent;
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
import com.imagine.cloud.bean.ShareBean;
import com.imagine.cloud.dao.CheckBoughtDao;
import com.imagine.cloud.dao.MeetingInfoDao;
import com.imagine.cloud.dao.MessageDao;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.util.ShareUtil;
import com.imagine.cloud.widget.StateTextView;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.umeng.socialize.UMShareAPI;
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
    @InjectView(R.id.bao_icon)
    ImageView baoIcon;
    @InjectView(R.id.share_bt)
    LinearLayout shareBt;

    private MeetingInfoDao meetingInfoDao;
    private MeetingDetailBean meetingDetailBean;
    private String id;
    private CheckBoughtDao checkBoughtDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.inject(this);
        id = getStringExtras("id", "");
        meetingInfoDao = new MeetingInfoDao(this, this);
        meetingInfoDao.getInfo(AppUtil.getUserId(this), id);
        checkBoughtDao = new CheckBoughtDao(this, this);
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
            buyNum.setText("报名");
            //判断报名的类型，是否可以报名
            if ("0".equals(meetingDetailBean.getType())) {
                baomingContainer.setEnabled(true);
                baomingContainer.setVisibility(View.VISIBLE);
                baoIcon.setImageResource(R.drawable.baoming);
                //不可以报名
            } else if ("1".equals(meetingDetailBean.getType())) {
                baomingContainer.setVisibility(View.GONE);
                //已过期
            } else if ("2".equals(meetingDetailBean.getType())) {
                buyNum.setText("已过期");
                baoIcon.setImageResource(R.drawable.baoming_un);
                buyNum.setTextColor(ContextCompat.getColor(this, R.color.text_color_light));
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
        } else if (requestCode == RequestCode.CODE_6) {
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("title", meetingDetailBean.getTitle());
            bundle.putString("price", meetingDetailBean.getPrice());
            transUi(SignUpActivity.class, bundle);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("会议详情");
    }

    @OnClick({R.id.zan_container, R.id.baoming_container, R.id.collect_container,R.id.share_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zan_container:
                if (AppUtil.chckeLogin(this, true)){
                    meetingInfoDao.addLike(AppUtil.getUserId(this), id);
                    showProgress(true);
                }
                break;
            case R.id.baoming_container:
                if (AppUtil.chckeLogin(this, true)) {
                    checkBoughtDao.checkBought(id, AppUtil.getUserId(this));
                    showProgress(true);
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
            case R.id.share_bt:
                if(meetingDetailBean!=null) {
                    ShareBean bean = new ShareBean();
                    bean.setTitle(meetingDetailBean.getTitle());
                    bean.setUrl(meetingDetailBean.getShare());
                    bean.setImgUrl(Requst.BASE_IMG_URL+meetingDetailBean.getImg());
                    bean.setDes(meetingDetailBean.getSubtitle());
                    ShareUtil.getInstance(this).share(bean, this);
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
    }

}
