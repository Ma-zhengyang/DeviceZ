package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryBean {

    private int titleRes;
    private String availableValue;
    private String totalValue;
    private int progress;

    public MemoryBean(int titleRes, String availableValue, String totalValue, int progress) {
        this.titleRes = titleRes;
        this.availableValue = availableValue;
        this.totalValue = totalValue;
        this.progress = progress;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public String getAvailableValue() {
        return availableValue;
    }

    public void setAvailableValue(String availableValue) {
        this.availableValue = availableValue;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
