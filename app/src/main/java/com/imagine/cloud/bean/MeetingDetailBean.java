package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingDetailBean
 */

public class MeetingDetailBean {

    /**
     * islike : 0
     * isfav : 0
     * id : 58
     * title : 保存您最喜爱的站点。 视频教程
     * content :
     * create_time : 26169196819
     * die_time :
     * img :
     * type
     * like_num : 0
     * fav_num : 0
     *
     * subtitle : 保存您最喜爱的站点。 视频教程
     *
     *
     *
     */

    String type ;
    private int islike;
    private int isfav;
    private String id;
    private String title;
    private String content;
    private String create_time;
    private String die_time;
    private String img;
    private String like_num;
    private String fav_num;
    private String subtitle;
    private String url ;
    private String buy_num ;
    private String price ;
    private String share ;


    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getIsfav() {
        return isfav;
    }

    public void setIsfav(int isfav) {
        this.isfav = isfav;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDie_time() {
        return die_time;
    }

    public void setDie_time(String die_time) {
        this.die_time = die_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getFav_num() {
        return fav_num;
    }

    public void setFav_num(String fav_num) {
        this.fav_num = fav_num;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }


    @Override
    public String toString() {
        return "MeetingDetailBean{" +
                "islike=" + islike +
                ", isfav=" + isfav +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", die_time='" + die_time + '\'' +
                ", img='" + img + '\'' +
                ", like_num='" + like_num + '\'' +
                ", fav_num='" + fav_num + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
