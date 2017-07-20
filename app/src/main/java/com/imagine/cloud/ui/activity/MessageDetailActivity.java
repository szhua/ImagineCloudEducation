package com.imagine.cloud.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.MessageDetailBean;
import com.imagine.cloud.dao.MessageDao;
import com.runer.net.RequestCode;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageDetailActivity extends BaseActivity {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.message_title)
    TextView messageTitle;
    @InjectView(R.id.message_time)
    TextView messageTime;
    @InjectView(R.id.theme)
    TextView theme;
    @InjectView(R.id.msg_content)
    TextView msgContent;
    private MessageDao messageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.inject(this);
        String id = getStringExtras("id", "");
        messageDao = new MessageDao(this, this);
        messageDao.getMeesageInfo(id);
        messageDao.setMsgRead(id);
        showProgress(true);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_2){

            MessageDetailBean messageDetailBean =messageDao.getMessageDetailBean();
            if(messageDetailBean!=null){
                messageTitle.setText(messageDetailBean.getTitle());
                messageTime.setText(messageDetailBean.getCreate_time());
                theme.setText("会议主题"+messageDetailBean.getSubtitle());
                msgContent.setText(messageDetailBean.getContent());
            }

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("消息详情");
    }
}
