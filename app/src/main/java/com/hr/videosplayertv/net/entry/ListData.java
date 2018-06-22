package com.hr.videosplayertv.net.entry;

public class ListData {

    private int type = 1;

    private String title;

    public ListData() {
    }

    public ListData(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
