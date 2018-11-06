package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-2.
 */

public class MainBean {

    private int background;
    private int imgRes;
    private int titleRes;
    private int id;

    public MainBean(int background, int imgRes, int titleRes, int id) {
        this.background = background;
        this.imgRes = imgRes;
        this.titleRes = titleRes;
        this.id = id;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
