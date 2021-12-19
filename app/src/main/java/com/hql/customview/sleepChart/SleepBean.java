package com.hql.customview.sleepChart;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/17
 * <br /> Description :
 */
public class SleepBean {
    /**
     * 一段的比例，满为100
     */
    private int percent;
    /**
     * 类型 ：熟睡、浅睡、清醒
     */
    private String type;
    public final static String TYPE_SLEEP_DEEP = "TYPE_SLEEP_DEEP";
    public final static String TYPE_SLEEP_SHALLOW = "TYPE_SLEEP_SHALLOW";
    public final static String TYPE_SLEEP_SOBER = "TYPE_SLEEP_SOBER";

    public SleepBean(int percent, String type) {
        this.percent = percent;
        this.type = type;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
