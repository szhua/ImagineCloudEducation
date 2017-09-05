package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebFragment;
import com.imagine.cloud.bean.CourseDetailBean;
import com.imagine.cloud.bean.MeetingDetailBean;
import com.imagine.cloud.bean.ShareBean;
import com.imagine.cloud.dao.CourseDao;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.service.MusicInfoDetail;
import com.imagine.cloud.service.MusicPlayer;
import com.imagine.cloud.service.PlayEvent;
import com.imagine.cloud.service.PlayerService;
import com.imagine.cloud.util.AppUtil;
import com.imagine.cloud.util.ShareUtil;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import org.greenrobot.eventbus.EventBus;
import java.util.concurrent.TimeUnit;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/*音频课程详情页面*/
public class CourseDetailActivity extends BaseActivity {
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
    @InjectView(R.id.course_name)
    TextView courseName;
    @InjectView(R.id.course_time)
    TextView courseTime;
    @InjectView(R.id.course_num)
    TextView courseNum;
    @InjectView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @InjectView(R.id.container)
    LinearLayout container;
    @InjectView(R.id.play_bt)
    ImageView playBt;
    private Disposable flowable;
    private String id ;
    private CourseDao courseDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.inject(this);
        //开启音乐播放service;
        startService(new Intent(this, PlayerService.class));
        id =getStringExtras("id","") ;
        courseDao =new CourseDao(this,this) ;
        courseDao.getAudioCourseInfo(id);
        showProgress(true);

        //*********************************从外部进来的时候
        //当正在播放的情况下；
        if(MusicPlayer.getPlayer().isNowPlaying()&&MusicPlayer.getPlayer().getNowPlaying().getId().equals(id)){
            fisrtPlay =false;
            playBt.setImageResource(R.drawable.player_play);
            //更新显示的时间
            flowable = Flowable.interval(0,1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            //若是正在播放
                            if(MusicPlayer.getPlayer().isNowPlaying()){
                                showProgress(false);
                                courseTime.setText(AppUtil.timeParse(MusicPlayer.getPlayer().getCurrentPosition()));
                            }
                        }
                    });
            //*****************************************************************************************************************************
        }
    }
    private boolean fisrtPlay=true;
    @OnClick(R.id.play_bt)
    public void onViewClicked() {
        if(courseDetailBean!=null){
            playMusic();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        //分享
        setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(courseDetailBean!=null) {
                        ShareBean bean = new ShareBean();
                        bean.setTitle(courseDetailBean.getTitle());
                        bean.setUrl(courseDetailBean.getShare());
                        bean.setImgUrl(Requst.BASE_IMG_URL+courseDetailBean.getImg());
                        bean.setDes(courseDetailBean.getSubtitle());
                        ShareUtil.getInstance(CourseDetailActivity.this).share(bean, CourseDetailActivity.this);
                }
            }
        });

    }
    private CourseDetailBean courseDetailBean ;
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        //设置可课程详情
        if(requestCode== RequestCode.CODE_0){
            courseDetailBean =courseDao.getCourseDetailBean() ;
            if(courseDetailBean!=null){
                addFragmentList(R.id.container, BaseWebFragment.getInstance(courseDetailBean.getUrl()));
                courseName.setText(courseDetailBean.getTitle());
                if(MusicPlayer.getPlayer().getNowPlaying()==null||!id.equals(MusicPlayer.getPlayer().getNowPlaying().getId()))
                courseTime.setText(courseDetailBean.getTime());
                courseNum.setText(courseDetailBean.getNumb()+"人已收听");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源,停止计时
        if(flowable!=null)
        flowable.dispose();
    }

    public void playMusic(){
        if(fisrtPlay) {
            //播放；
            showProgressWithMsg(true,"正在解析音频资源...");
            PlayEvent playEvent = new PlayEvent();
            MusicInfoDetail musicInfoDetail = new MusicInfoDetail();
            musicInfoDetail.setUri(courseDetailBean.getVoice());
            musicInfoDetail.setTitle(courseDetailBean.getTitle());
            musicInfoDetail.setId(id);
            playEvent.setAction(PlayEvent.Action.PLAY);
            playEvent.setSong(musicInfoDetail);
            EventBus.getDefault().post(playEvent);
            playBt.setImageResource(R.drawable.player_pause);
        }else{
            //暂停
            if(MusicPlayer.getPlayer().isNowPlaying()){
                PlayEvent playEvent = new PlayEvent();
                playEvent.setAction(PlayEvent.Action.PAUSE);
                EventBus.getDefault().post(playEvent);
                //播放完成后重新播放
                playBt.setImageResource(R.drawable.player_pause);
            }else {
                PlayEvent playEvent = new PlayEvent();
                playEvent.setAction(PlayEvent.Action.RESUME);
                EventBus.getDefault().post(playEvent);
                playBt.setImageResource(R.drawable.player_play);
            }
        }


        //监听播放状态并且设置图片
        MusicPlayer.getPlayer().setOnOneCompeleListener(new MusicPlayer.OnOnePlayListener() {
            @Override
            public void onCompelete() {
                playBt.setImageResource(R.drawable.player_pause);
            }
            @Override
            public void onError() {
                UiUtil.showLongToast(getApplicationContext(),"解析出错");
                fisrtPlay =true ;
                showProgress(false);
                playBt.setImageResource(R.drawable.player_pause);
            }
            @Override
            public void onPrepared() {
                fisrtPlay =false ;
                showProgress(false);
                playBt.setImageResource(R.drawable.player_play);
            }
        });

        //更新显示的时间
        flowable = Flowable.interval(0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //若是正在播放
                        if(MusicPlayer.getPlayer().isNowPlaying()){
                            showProgress(false);
                            courseTime.setText(AppUtil.timeParse(MusicPlayer.getPlayer().getCurrentPosition()));
                        }
                    }
                });
    }

}
