package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class ScreenBean {

    private int imgRes;
    private int titleRes;
    private int describeRes;

    public ScreenBean(int imgRes, int titleRes, int describeRes) {
        this.imgRes = imgRes;
        this.titleRes = titleRes;
        this.describeRes = describeRes;
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

    public int getDescribeRes() {
        return describeRes;
    }

    public void setDescribeRes(int describeRes) {
        this.describeRes = describeRes;
    }
}
