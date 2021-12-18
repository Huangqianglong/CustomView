package com.hql.customview;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/17
 * <br /> Description :
 */
public class SleepHorizontalAxis {
    String type;
    String text;
    int percent;
    public SleepHorizontalAxis(String type, String text,int persent) {
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
