package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.base.BaseWebFragment;
import com.imagine.cloud.bean.MeetingDetailBean;
import com.imagine.cloud.bean.MeetingOrderBean;
import com.imagine.cloud.bean.ShareBean;
import com.imagine.cloud.dao.MeetingProDetailDao;
import com.imagine.cloud.dao.MessageDao;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.util.ShareUtil;
import com.runer.net.RequestCode;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProjectDetailActivity extends BaseActivity {


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
    @InjectView(R.id.yaoling_bt)
    LinearLayout yaolingBt;
    @InjectView(R.id.keti_bt)
    LinearLayout ketiBt;
    @InjectView(R.id.lixiang_bt)
    LinearLayout lixiangBt;
    @InjectView(R.id.liucheng_bt)
    LinearLayout liuchengBt;
    @InjectView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @InjectView(R.id.container)
    LinearLayout container;

    private MeetingProDetailDao meetingProDetailDao;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        ButterKnife.inject(this);
        proId = getStringExtras("id", "");
        meetingProDetailDao = new MeetingProDetailDao(this, this);
        meetingProDetailDao.getInfo(MeetingProDetailDao.PRO_INFO_TYPE, AppUtil.getUserId(this), proId);
        //若是从消息中过来的时候
        String type =getStringExtras("type","0");
        if("msg".equals(type)){
            MessageDao messageDao =new MessageDao(this,this) ;
            String msgId =getStringExtras("msg_id","");
            messageDao.setMsgRead(msgId);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getStringExtras("title",""));
        setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meetingProDetailDao!=null&&meetingProDetailDao.getDetail()!=null){
                    MeetingDetailBean  meetingDetailBean = meetingProDetailDao.getDetail();;
                    if(meetingDetailBean!=null) {
                        ShareBean bean = new ShareBean();
                        bean.setTitle(meetingDetailBean.getTitle());
                        bean.setUrl(meetingDetailBean.getShare());
                        bean.setImgUrl(Requst.BASE_IMG_URL+meetingDetailBean.getImg());
                        bean.setDes(meetingDetailBean.getSubtitle());
                        ShareUtil.getInstance(ProjectDetailActivity.this).share(bean, ProjectDetailActivity.this);
                    }
                }
            }
        });
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            MeetingDetailBean detail = meetingProDetailDao.getDetail();
            if (detail != null) {
                setTitle(detail.getTitle());
                addFragmentList(R.id.container, BaseWebFragment.getInstance(detail.getUrl()));
            }
        }
    }

    @OnClick({R.id.yaoling_bt, R.id.keti_bt, R.id.lixiang_bt, R.id.liucheng_bt})
    public void onViewClicked(View view) {
        Bundle bundle =new Bundle() ;
        switch (view.getId()) {
            case R.id.yaoling_bt:
                bundle.putString(BaseWebAcitivity.WEB_URL, Requst.YAO_LING_URL+proId);
                bundle.putString(BaseWebAcitivity.WEB_TITLE,"申报要领");
                transUi(BaseWebAcitivity.class,bundle);
                break;
            case R.id.keti_bt:
                bundle.putString(BaseWebAcitivity.WEB_URL, Requst.FEN_XI_URL+proId);
                bundle.putString(BaseWebAcitivity.WEB_TITLE,"课题解析");
                transUi(BaseWebAcitivity.class,bundle);
                break;
            case R.id.lixiang_bt:
                bundle.putString(BaseWebAcitivity.WEB_URL, Requst.TONG_JI_URL+proId);
                bundle.putString(BaseWebAcitivity.WEB_TITLE,"立项统计");
                transUi(BaseWebAcitivity.class,bundle);
                break;
            case R.id.liucheng_bt:
                transUi(DeclarationProcessActivity.class,null);
                break;
        }
    }
}
