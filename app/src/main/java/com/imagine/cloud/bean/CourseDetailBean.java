package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/13/013.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseDetailBean
 */

public class CourseDetailBean {

    /*{"author":"e让我","time":"04:21","title":"特威尔玩儿","file":"14999154306076.mp3",
    "url":"http://192.168.0.85/changxiang/api/voice.php?id=1",
    "voice":"http://192.168.0.85/changxiang/upload/test/file/14999154306076.mp3"}*/
    String author ;
    String time ;
    String title ;
    String file ;
    String url ;
    String voice ;
    String numb ;
    String subtitle;
    String img ;
    String share ;


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
}
