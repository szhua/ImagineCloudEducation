package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/15/015.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MessageBean
 */

public class MessageBean {

    /*{"id":"126","title":"635","subtitle":"62465254365","create_time":"2017-07-15 11-45-21","type":"3","message_type":"纯文本消息"}*/
    String id ;
    String title ;
    String subtitle ;
    String create_time ;
    String type ;
    String message_type ;
    String mp_id ;
    String status ;




    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMp_id() {
        return mp_id;
    }

    public void setMp_id(String mp_id) {
        this.mp_id = mp_id;
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

    public String getCreate_tiem() {
        return create_time;
    }

    public void setCreate_tiem(String create_tiem) {
        this.create_time = create_tiem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }
}
