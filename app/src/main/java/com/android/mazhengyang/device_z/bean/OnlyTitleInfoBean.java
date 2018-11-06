package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class OnlyTitleInfoBean {

    private int titleRes;
    private String info;

    public OnlyTitleInfoBean(int titleRes, String info) {
        this.titleRes = titleRes;
        this.info = info;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
