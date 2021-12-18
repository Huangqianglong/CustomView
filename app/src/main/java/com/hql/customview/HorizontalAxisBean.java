package com.hql.customview;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/15
 * <br /> Description :柱形图的横向数据
 */
public class HorizontalAxisBean {
    private String text;
    private float value;

    public HorizontalAxisBean(String text, int value) {
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
}
