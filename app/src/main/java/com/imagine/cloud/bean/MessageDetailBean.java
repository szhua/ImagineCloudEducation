package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/17/017.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MessageDetailBean
 */

public class MessageDetailBean {


    /*"type":"3","message_type":"纯文本消息","title":"45263425435342","subtitle":"52352352345","create_time":"2017-07-15 11-50-23","content":""*/

    String type ;
    String message_type ;
    String title ;
    String subtitle ;
    String create_time ;
    String content ;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "MessageDetailBean{" +
                "type='" + type + '\'' +
                ", message_type='" + message_type + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", create_time='" + create_time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
