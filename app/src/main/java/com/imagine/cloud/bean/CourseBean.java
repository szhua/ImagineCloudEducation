package com.imagine.cloud.bean;

/**
 * Created by szhua on 2017/7/13/013.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * CourseBean
 */

public class CourseBean {
    /*{"id":"2","title":"我亲热我","author":" 请问请问 ","time":"04:21","numb":"1065"}*/
    String id ;
    String title ;
    String author ;
    String time ;
    String numb ;
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

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", numb='" + numb + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
