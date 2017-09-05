package com.imagine.cloud.base;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.imagine.cloud.R;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.runer.liabary.util.UiUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaseWebAcitivity extends BaseActivity {


    public static final String WEB_TITLE ="title" ;
    public static final String WEB_URL="url" ;

    @InjectView(R.id.container)
    LinearLayout container;
    private String title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_acitivity);
        ButterKnife.inject(this);
       title = getStringExtras(WEB_TITLE,"网页标题");
       String url = getStringExtras(WEB_URL,"https://github.com/Justson/AgentWeb/issues") ;
        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(new ChromeClientCallbackManager.ReceivedTitleCallback() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                      //  UiUtil.showLongToast(getApplicationContext(),title);
                    }
                })
                //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(title);
    }
}
