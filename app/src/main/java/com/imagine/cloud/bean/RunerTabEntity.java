package com.imagine.cloud.bean;

import com.imagine.cloud.R;
import com.runer.liabary.tab.listener.CustomTabEntity;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * RunerTabEntity
 */

public class RunerTabEntity implements CustomTabEntity  {


        private String title ;
        public RunerTabEntity (String title){
            this.title =title ;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return R.mipmap.download;
        }

        @Override
        public int getTabUnselectedIcon() {
            return R.mipmap.download;
        }
}
