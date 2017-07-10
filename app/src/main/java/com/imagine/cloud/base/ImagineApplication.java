package com.imagine.cloud.base;

import android.app.Application;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * ImagineApplication
 */
public class ImagineApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
        Logger.t(Constant.TAG);
    }
}
