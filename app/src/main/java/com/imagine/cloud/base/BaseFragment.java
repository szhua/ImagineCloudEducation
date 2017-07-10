package com.imagine.cloud.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.runer.liabary.dialog.ProgressHUD;
import com.runer.liabary.util.UiUtil;
import com.runer.net.interf.INetResult;

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
}
