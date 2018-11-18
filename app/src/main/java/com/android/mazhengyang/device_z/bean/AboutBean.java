package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-15.
 */

public class AboutBean {

    private int imgRes;
    private int titleRes;
    private String info;

    public AboutBean(int imgRes, int titleRes, String info) {
        this.imgRes = imgRes;
        this.titleRes = titleRes;
        this.info = info;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
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
