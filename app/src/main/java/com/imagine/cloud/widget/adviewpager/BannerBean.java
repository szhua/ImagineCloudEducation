package com.imagine.cloud.widget.adviewpager;

/**
 * Created by Administrator on 2017/5/15.
 */

public class BannerBean {

    /**
     * title : 轮播图测试1
     * url :  热太热特
     * img : 14992471492697.jpg
     * create_time : 2017-07-11
     */

    private String title;
    private String url;
    private String img;
    private String create_time;
    String type ;
    /**
     * id : 24
     * mp_id : 75
     */

    private String id;
    private String mp_id;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", create_time='" + create_time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMp_id() {
        return mp_id;
    }

    public void setMp_id(String mp_id) {
        this.mp_id = mp_id;
    }
}
