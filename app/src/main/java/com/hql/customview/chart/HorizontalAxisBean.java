package com.hql.customview.chart;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/15
 * <br /> Description : 柱形图的柱形数据
 */
public class HorizontalAxisBean {
    /**
     * 横轴描述文本
     */
    private String text;
    /**
     * 柱形图的y值
     */
    private float value;
    /**
     * 自定义数据，做状态
     */
    private String lab;
    /**
     * 自定义数据，做状态描述
     */
    private int labState;

    /**
     * 按比例画
     */
    private int [] ratio;

    public HorizontalAxisBean(String text, float value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public int getLabState() {
        return labState;
    }

    public void setLabState(int labState) {
        this.labState = labState;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int[] getRatio() {
        return ratio;
    }

    public void setRatio(int[] ratio) {
        this.ratio = ratio;
    }
}
