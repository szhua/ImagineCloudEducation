package com.imagine.cloud.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.imagine.cloud.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Rcsd
 * Create   2017/5/12 14:02;
 * https://github.com/szhua
 * @author sz.hua
 */
public class CodeButton extends AppCompatTextView {

    private static final int totalCount =60;
    private  Timer timer;

    private int currentCount =totalCount;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(currentCount>1){
                setEnabled(false);
                currentCount--;
                setText(currentCount+"秒后重新获取");
            }else{
                if(timer!=null){
                    timer.cancel();
                }
                currentCount =totalCount ;
                setText("重新发送校验码");
                setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_theme));
                setEnabled(true);
            }
        }
    };
    private TimerTask timerTask;


    public CodeButton(Context context) {
        super(context);
    }
    public CodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        timer =new Timer();
//        setText(context.getResources().getString(R.string.input_code));
    }
    //开始计时
    public void startNumCode(){
        setEnabled(false);
        setTextColor(ContextCompat.getColor(getContext(),R.color.text_color_light));
        if(timer!=null){
            timer =null ;
            timerTask =null ;
        }
         timer =new Timer() ;
         timerTask =new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(timerTask,0,1000);
    }
}
