package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingBean
 */

public class MeetingBean {

/* "id": "49",
    "title": "测试图片上传",
    "subtitle": ",如果两个数组存在相同的key,后面的一个会覆盖前面的 ",
    "create_time": "2017-07-06",
    "img": "14993103291689.jpg"*/

String id ;
    String title ;
    String subtitle ;
    String create_time ;
    String img ;

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
