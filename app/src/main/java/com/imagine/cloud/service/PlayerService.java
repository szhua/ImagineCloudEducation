package com.imagine.cloud.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.imagine.cloud.R;
import com.imagine.cloud.ui.activity.HomeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.drm.DrmStore.Action.PLAY;

/**
 * Created by szhua on 2017/7/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * PlayerService
 */

public class PlayerService extends Service {
    //管理通知
    private NotificationManager manager = null;
    //通知ID
    private final int NOTIFICATION_ID = 10001;
    private RemoteViews remoteViews;
    private final String MUSIC_NOTIFICATION_ACTION_PLAY = "ACTION_PLAY";
    private final String MUSIC_NOTIFICATION_ACTION_NEXT = "ACTION_NEXT";
    private final String MUSIC_NOTIFICATION_ACTION_PREV = "ACTION_PREV";
    private final String MUSIC_NOTIFICATION_ACTION_CLOSE = "ACTION_CLOSE";
    private final String MUSIC_NOTIFICATION_ACTION_LOVE = "ACTION_LOVE";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
    }


    //开启通知
    public void startNM() {
//        //点击跳转主界面
        Intent jumpIntent = new Intent(this, HomeActivity.class);
        PendingIntent jump = PendingIntent.getActivity(this, 0, jumpIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notice, jump);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //创建通知栏控制
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //上一曲
        Intent prev = new Intent();
        prev.setAction(MUSIC_NOTIFICATION_ACTION_PREV);
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, prev, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.prev, intent_prev);
     //   remoteViews.setImageViewResource(R.id.prev, R.drawable.note_btn_pre);
        //下一曲
        Intent next = new Intent();
        next.setAction(MUSIC_NOTIFICATION_ACTION_NEXT);
        PendingIntent intent_next = PendingIntent.getBroadcast(this, 2, next, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next, intent_next);
      //  remoteViews.setImageViewResource(R.id.next, R.drawable.note_btn_next);
        //收藏
        Intent love = new Intent();
        love.setAction(MUSIC_NOTIFICATION_ACTION_LOVE);
        PendingIntent intent_love = PendingIntent.getBroadcast(this, 4, love, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play, intent_love);
       // remoteViews.setImageViewResource(R.id.love, R.drawable.note_btn_loved);
        mBuilder
                .setContent(remoteViews)
               // .setSmallIcon(R.drawable.stat_notify)
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setTicker("网易云音乐正在播放")
                .setWhen(System.currentTimeMillis());

        Notification notify = mBuilder.build();
        manager.notify(NOTIFICATION_ID, notify);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startNM();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //单曲进行播放；
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayEvent playevent) {
        switch (playevent.getAction()) {
            case PLAY:
                MusicPlayer.getPlayer().play(playevent.getSong());
                break;
            case PAUSE:
                MusicPlayer.getPlayer().pause();
                break;
            case RESUME:
                MusicPlayer.getPlayer().resume();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
