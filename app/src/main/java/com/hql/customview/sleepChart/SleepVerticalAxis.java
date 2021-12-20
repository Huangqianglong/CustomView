package com.hql.customview.sleepChart;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/17
 * <br /> Description : 睡眠数据
 */
public class SleepVerticalAxis {
    /**
     * 睡眠数据类型：深睡 浅睡 清醒
     */
    String type;
    /**
     * 纵轴右侧的文字
     */
    String text;
    /**
     * 占横轴的百分比
     */
    int percent;
    public SleepVerticalAxis(String type, String text, int persent) {
        this.type = type;
        this.text = text;
        this.percent = persent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
