package com.hql.customview.heartbeat;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/21
 * <br /> Description :
 */
public class HeartBeatBean {
    int rate;
    String time;
    /**
     * ":"正常",//标签
     */
    String label;
    /**
     * //":"2",// 1:偏低  2:正常  3:偏高
     */
    int labelStatus;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(int labelStatus) {
        this.labelStatus = labelStatus;
    }
}
