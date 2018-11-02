package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class BaseBean {

    private String title;
    private String info;

    public BaseBean(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
