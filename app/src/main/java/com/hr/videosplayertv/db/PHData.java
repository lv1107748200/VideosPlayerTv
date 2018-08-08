package com.hr.videosplayertv.db;


import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/*
 * lv   2018/7/16  播放记录
 */
@RealmClass
public class PHData implements RealmModel {
    @PrimaryKey
    private String ID;
    private String key;
    private String name;
    private String Contxt;
    private String ImgPath;
    private String Title;
    private String url;
    private long time;
    private long press;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContxt() {
        return Contxt;
    }

    public void setContxt(String contxt) {
        Contxt = contxt;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPress() {
        return press;
    }

    public void setPress(long press) {
        this.press = press;
    }
}
