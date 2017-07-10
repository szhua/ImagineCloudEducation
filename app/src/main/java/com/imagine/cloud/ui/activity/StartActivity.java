package com.imagine.cloud.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

//启动页；
public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Flowable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d("跳转"+aLong);
                        transUi(HomeActivity.class,null);
                        finish();
                    }
                });
    }
}
