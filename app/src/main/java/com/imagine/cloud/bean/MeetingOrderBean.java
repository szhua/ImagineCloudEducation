package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/20/020.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MeetingOrderBean
 */

public class MeetingOrderBean {
    /**
     * id : 51
     * create_time :
     * title : 22017-7-184特瑞
     * subtitle : ,如果两个数组存在相同的key,后面的一个会覆盖前面的 ,如果两个数组存在相同的key,后面的一个会覆盖前面的
     * status : 0
     */
    private String id;
    private String create_time;
    private String title;
    private String subtitle;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeetingOrderBean{" +
                "id='" + id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
