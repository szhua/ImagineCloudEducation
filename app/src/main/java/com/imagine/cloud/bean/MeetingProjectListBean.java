package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/12/012.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingProjectListBean
 */

public class MeetingProjectListBean  {


    String id ;
    String title ;
    String subtitle ;
    String create_time ;
    String img ;
    String fav_id ;
    String info_type ;

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
