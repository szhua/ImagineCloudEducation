package com.imagine.cloud.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.runer.liabary.dialog.ProgressHUD;
import com.runer.liabary.util.UiUtil;
import com.runer.net.interf.INetResult;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Rcsd
 * Create   2017/5/11 13:35;
 * https://github.com/szhua
 *
 * @author sz.hua
 */
public class BaseFragment extends Fragment implements INetResult ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {


    private ProgressHUD mProgressHUD;

    @Override
    public void onRequestSuccess(int requestCode) {
        onCompeleteRefresh();
        if(isAdded()&&isVisible()) {
            showProgress(false);
        }else{
            return;
        }
  }

    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        onCompeleteRefresh();
        if (isAdded()&&isVisible()) {
            showProgress(false);
            UiUtil.showLongToast(getContext(), errorInfo);
        }else{
            return;
        }
    }

    @Override
    public void onRequestFaild(int requestCode, String errorNo, String errorMessage) {
        onCompeleteRefresh();
        if (isAdded()&&isVisible()){
            showProgress(false);
            UiUtil.showLongToast(getContext(), errorNo);
        }else{
            return;
        }
    }


    @Override
    public void onNoConnect() {
        onCompeleteRefresh();
        if (isAdded()&&isVisible()) {
            showProgress(false);
            UiUtil.showLongToast(getContext(), getString(R.string.no_net));
        }else{
            return;
        }
    }

    public void showProgress(boolean show) {
        if (this.isAdded()) {
            showProgressWithMsg(show, getString(R.string.loading));
        }
    }

    /* 显示加载进度条*/
    public void showProgressWithMsg(boolean show, String msg) {

        if (this.isAdded()&&isVisible()) {
            if (show) {
                mProgressHUD = ProgressHUD.show(getContext(), msg, true, true, null);
            } else {
                if (mProgressHUD != null) {
                    mProgressHUD.dismiss();
                }
            }
        }
    }


    public int getColor(@ColorRes int color){
        return ContextCompat.getColor(getContext(), color) ;
    }


    public void transUi(Class activity ,Bundle bundle){
        Intent intent =new Intent(getContext(),activity) ;
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
    }

    public void onCompeleteRefresh(){

    }

    @Override
    public void onLoadMoreRequested() {

    }


    public View getEmptyView(String notice){
        View view = View.inflate(getContext(),R.layout.nor_empty_layout,null) ;
        TextView noticeView = (TextView) view.findViewById(R.id.text);
        noticeView.setText(notice);
        return  view ;
    }
    public View getEmptyView(){
        return  View.inflate(getContext(),R.layout.nor_empty_layout,null) ;
    }

    public View getEmptyViewFixSize(String notice){
        View view = View.inflate(getContext(),R.layout.nor_fix_empty_view,null) ;
        TextView noticeView = (TextView) view.findViewById(R.id.text);
        noticeView.setText(notice);
        return  view ;
    }

    public int [] getRefreshColor(Context context){
        int [] colors = new int[3] ;
        colors[0] =ContextCompat.getColor(context,android.R.color.holo_orange_dark) ;
        colors[1] =ContextCompat.getColor(context,R.color.albumColorPrimary) ;
        colors[2] =ContextCompat.getColor(context,android.R.color.holo_red_light) ;
        return  colors;
    }

    /**
     * 隐藏软键
     */
    public void hideInputMethod(final EditText v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    /**
     * 显示软键盘
     * @param v
     */
    public void showInputMethod(final EditText v) {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager)getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v,0);
    }
}
