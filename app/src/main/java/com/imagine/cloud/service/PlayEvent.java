package com.imagine.cloud.service;


import java.util.List;

/**
 * Created by Limuyang on 2016/9/6.
 * 播放事件控制
 */

public class PlayEvent {
    public enum Action {
        PLAY, PAUSE, RESUME, NEXT, PREVIOES, SEEK
    }

    private Action mAction;
    private MusicInfoDetail mDetail;
    private List<MusicInfoDetail> mQueue;
    private int seekTo;
    private int position;




    public MusicInfoDetail getSong() {
        return mDetail;
    }

    public void setSong(MusicInfoDetail detail) {
        mDetail = detail;
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    public List<MusicInfoDetail> getQueue() {
        return mQueue;
    }

    public void setQueue(List<MusicInfoDetail> queue) {
        mQueue = queue;
    }


    public void setCurrentIndex(int currentPosition) {
        position = currentPosition;
    }

    public int getCurrentIndex() {
        return position;
    }

    public int getSeekTo() {
        return seekTo;
    }

    //定位
    public void setSeekTo(int seekTo) {
        this.seekTo = seekTo;
    }
}
