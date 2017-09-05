package com.imagine.cloud.bean;

import com.imagine.cloud.R;

/**
 * Created by szhua on 2017/8/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * ShareBean
 */

public class ShareBean {
    String title;
    String des;
    String url;
    String imgUrl;
    int defaultImage = R.drawable.logo;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

}
