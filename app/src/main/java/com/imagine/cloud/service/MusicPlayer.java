package com.imagine.cloud.service;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import java.io.IOException;


/*单任务防止内存泄漏*/
public class MusicPlayer implements OnCompletionListener {

    private static MusicPlayer player = new MusicPlayer();
    private MediaPlayer mMediaPlayer;
    private boolean isNowPlaying;
    private boolean isFinished;
    private MusicInfoDetail musicInfoDetail;

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }
    public void setmMediaPlayer(MediaPlayer mMediaPlayer) {
        this.mMediaPlayer = mMediaPlayer;
    }

    public static void setPlayer(MusicPlayer player) {
        MusicPlayer.player = player;
    }
    public static MusicPlayer getPlayer() {

        //若是release的情况下；
        if(player.getmMediaPlayer()==null){
            MusicPlayer  musicPlayer =  new MusicPlayer();
            player =musicPlayer;
        }
        return player;
    }

    public MusicPlayer() {
        mMediaPlayer = new ManagedMediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
    }

    public void play(MusicInfoDetail detail) {
        try {
            this.musicInfoDetail = detail;
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(detail.getUri());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (onOnePlayListener != null) {
                        onOnePlayListener.onPrepared();
                    }
                    mMediaPlayer.start();
                    setNowPlaying(true);
                    setNowFinished(false);
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    setNowPlaying(false);
                    setNowFinished(false);
                    if (onOnePlayListener != null) {
                        onOnePlayListener.onError();
                    }
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            setNowPlaying(false);
            setNowFinished(false);
            if (onOnePlayListener != null) {
                onOnePlayListener.onError();
            }
        }
    }

    public void pause() {
        mMediaPlayer.pause();
        setNowPlaying(false);
    }

    public void resume() {
        setNowPlaying(true);
        mMediaPlayer.start();
    }

    //移动进度；
    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    //播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        setNowPlaying(false);
        setNowFinished(true);
        if (onOnePlayListener != null) {
            onOnePlayListener.onCompelete();
        }
    }

    private MusicInfoDetail getNowPlaying() {
        return musicInfoDetail;
    }

    //获得当前播放信息;
    public int getCurrentPosition() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //获得时长；
    public int getDuration() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    public void release(){
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
    public boolean isNowFinished() {
        return isFinished;
    }

    public void setNowFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isNowPlaying() {
        return isNowPlaying;
    }

    public void setNowPlaying(boolean nowPlaying) {
        isNowPlaying = nowPlaying;
    }


    //监听播放
    private OnOnePlayListener onOnePlayListener;

    public void setOnOneCompeleListener(OnOnePlayListener onOnePlayListener) {
        this.onOnePlayListener = onOnePlayListener;
    }

    public interface OnOnePlayListener {
        void onCompelete();
        void onError();
        //准备就绪
        void onPrepared();
    }


}