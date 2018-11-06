package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryBean {

    private int titleRes;
    private Long freeValue;
    private Long totalValue;

    public MemoryBean(int titleRes, Long freeValue, Long totalValue) {
        this.titleRes = titleRes;
        this.freeValue = freeValue;
        this.totalValue = totalValue;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public Long getFreeValue() {
        return freeValue;
    }

    public void setFreeValue(Long freeValue) {
        this.freeValue = freeValue;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }
}
