package com.wqdata.net.chart;



/**
 * Created by ruanlingfeng on 2016/1/11.
 */
public class TemperatureDatas  {
    private String babyId;
    private Long time;
    private float data;
    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
