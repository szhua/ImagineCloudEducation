package com.imagine.cloud.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.runer.liabary.dialog.ProgressHUD;
import com.runer.liabary.util.UiUtil;
import com.runer.net.interf.INetResult;

import java.io.Serializable;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Rcsd
 * Create   2017/5/11 11:52;
 * https://github.com/szhua
 *基础Activity
 * @author sz.hua
 */
public class BaseActivity extends AppCompatActivity implements INetResult ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

   private TextView title ;
   private ImageView leftImage ;
   private ImageView rightImage ;
   private TextView rightTv ;
   private ProgressHUD mProgressHUD;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void  setTitle(@NonNull String title){
        if(this.title!=null&& !TextUtils.isEmpty(title)){
            this.title.setText(title);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        title = (TextView) findViewById(R.id.title);
        leftImage = (ImageView) findViewById(R.id.left_back);
        rightImage = (ImageView) findViewById(R.id.right_img);
        rightTv = (TextView) findViewById(R.id.right_text);


        if(leftImage!=null) {
            leftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        onCompeleteRefresh();
        showProgress(false);
    }

    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        UiUtil.showLongToast(this,errorInfo);
        onCompeleteRefresh();
        showProgress(false);
    }
    @Override
    public void onRequestFaild(int requestCode, String errorNo, String errorMessage) {
        onCompeleteRefresh();
       UiUtil.showLongToast(this,errorNo);
        showProgress(false);
    }

    @Override
    public void onNoConnect() {
        onCompeleteRefresh();
        UiUtil.showLongToast(this,getString(R.string.no_net));
        showProgress(false);
    }

    public void setRightTextColor(@ColorRes int color){
        if(rightTv!=null){
            rightTv.setTextColor(ContextCompat.getColor(this, color));
        }
    }

    //设置可见返回
    public void setLeftImageVisible(int visible){
        if(leftImage!=null){
            if(visible==View.VISIBLE||visible==View.INVISIBLE||visible==View.GONE)
            leftImage.setVisibility(visible);
        }
    }
    //设置右标题
    public void setRightText(@NonNull  String rightText){
        if (rightTv!=null&&!TextUtils.isEmpty(rightText)) {
            rightTv.setVisibility(View.VISIBLE);
            rightTv.setText(rightText);
        }
    }

    //设置右图片
    public void setRightImage(@DrawableRes int imgRes){
        if (rightImage!=null){
            rightImage.setVisibility(View.VISIBLE);
            rightImage.setImageResource(imgRes);
        }
    }
    //设置左标题
    public  void setLeftImage(@DrawableRes int imgRes){
        if(leftImage!=null){
            leftImage.setVisibility(View.VISIBLE);
            leftImage.setImageResource(imgRes);
        }
    }

    //设置左边点击
    public  void setLeftImageClickListener(View.OnClickListener clickListener){
        if(leftImage!=null){
            leftImage.setOnClickListener(clickListener);
        }
    }

    //设置右边点击
    public void setRightImageClickListener(View.OnClickListener clickListener){
        if(rightImage!=null){
            rightImage.setOnClickListener(clickListener);
        }
    }
    //设置右边点击
    public void setRightTvClickLister(View.OnClickListener clickListener){
        if(rightTv!=null){
            rightTv.setOnClickListener(clickListener);
        }
    }

    //显示加载进度；
public void showProgress(boolean show){
    showProgressWithMsg(show,getString(R.string.loading));
}
    /* 显示加载进度条*/
    public void showProgressWithMsg(boolean show ,String msg) {
        if (show) {
            mProgressHUD = ProgressHUD.show(this,msg ,true,true,null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }
    /*添加fragment*/
    public void addFragmentList(@IdRes int id ,BaseFragment baseFragment){
        FragmentManager fragmentManager =getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, baseFragment);
        fragmentTransaction.commit();
    }
    /**
     * 隐藏软键
     */
    public void hideInputMethod(final EditText v) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    /**
     * 显示软键盘
     * @param v
     */
    public void showInputMethod(final EditText v) {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v,0);
    }

    //跳转界面
    public void transUi(Class activity ,Bundle bundle){
        Intent intent =new Intent(this,activity) ;
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    //获得intent数据；@1
    public String getStringExtras(String key ,String defaultStr ){
      Bundle bundle =  getIntent().getExtras() ;
        String result =defaultStr ;
      if(bundle!=null){
        result =   bundle.getString(key,defaultStr);
      }
      return  result ;
    }
    //获得intent数据；@2
    public int  getIntExtras(String key ,int defaultStr ){
        Bundle bundle =  getIntent().getExtras() ;
        int  result =defaultStr ;
        if(bundle!=null){
            result =  bundle.getInt(key,defaultStr);
        }
        return  result ;
    }
    //获得intent数据 @3
    public Object  getSerializableExtras(String key ,Object defaultResult ){
        Bundle bundle =  getIntent().getExtras() ;
        Object  result =defaultResult ;
        if(bundle!=null){
            result =  bundle.getSerializable(key);
        }
        return  result ;
    }


    public int [] getRefreshColor(Context context){
       int [] colors = new int[3] ;
        colors[0] =ContextCompat.getColor(context,android.R.color.holo_orange_dark) ;
        colors[1] =ContextCompat.getColor(context,R.color.albumColorPrimary) ;
        colors[2] =ContextCompat.getColor(context,android.R.color.holo_red_light) ;
        return  colors;
    }


    /*定义刷新*/
    @Override
    public void onRefresh() {

    }

    //定义完成刷新
    public void onCompeleteRefresh(){

    }
    //获得空的View；
    public View getEmptyView(){
        return  View.inflate(this,R.layout.nor_empty_layout,null) ;
    }
    //设置自定义文本;
    public View getEmptyView(String notice){
        View view = View.inflate(this,R.layout.nor_empty_layout,null) ;
        TextView noticeView = (TextView) view.findViewById(R.id.text);
        noticeView.setText(notice);
        return  view ;
    }
    //加载完成;
    @Override
    public void onLoadMoreRequested() {

    }
}
