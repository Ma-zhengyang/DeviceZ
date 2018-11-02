package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-2.
 */

public class MainBean {

    private int background;
    private String title;
    private int id;

    public MainBean(int id, String title, int background) {
        this.id = id;
        this.title = title;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
