package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class SensorBean {

    private int titleRes;
    private int imgRes;

    public SensorBean(int titleRes, int imgRes) {
        this.titleRes = titleRes;
        this.imgRes = imgRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
