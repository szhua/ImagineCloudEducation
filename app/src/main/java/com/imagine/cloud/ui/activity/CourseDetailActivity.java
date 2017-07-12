package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.base.BaseWebFragment;
import com.imagine.cloud.service.MusicInfoDetail;
import com.imagine.cloud.service.MusicPlayer;
import com.imagine.cloud.service.PlayEvent;
import com.imagine.cloud.service.PlayerService;
import com.imagine.cloud.util.AppUtil;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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


    int playSate ;
    private Disposable flowable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.inject(this);
        addFragmentList(R.id.container, BaseWebFragment.getInstance("https://www.python.org/"));
        //开启音乐播放service;
        startService(new Intent(this, PlayerService.class));
    }

    private boolean fisrtPlay=true;
    @OnClick(R.id.play_bt)
    public void onViewClicked() {
        playMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源,停止播放；
        if(flowable!=null)
        flowable.dispose();
        MusicPlayer.getPlayer().release();
        stopService(new Intent(this, PlayerService.class));


    }

    public void playMusic(){
        if(fisrtPlay) {
            //播放；
            showProgressWithMsg(true,"正在解析音频资源...");
            PlayEvent playEvent = new PlayEvent();
            MusicInfoDetail musicInfoDetail = new MusicInfoDetail();
            musicInfoDetail.setUri("http://www.170mv.com/kw/other.web.ri01.sycdn.kuwo.cn/resource/n1/20/85/173933130.mp3");
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
