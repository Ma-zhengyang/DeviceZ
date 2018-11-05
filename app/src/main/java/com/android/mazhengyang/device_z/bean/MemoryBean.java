package com.android.mazhengyang.device_z.bean;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryBean {

    private String title;
    private Long freeValue;
    private Long totalValue;

    public MemoryBean(String title, Long freeValue, Long totalValue) {
        this.title = title;
        this.freeValue = freeValue;
        this.totalValue = totalValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
