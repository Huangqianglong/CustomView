package com.hql.customview;

import java.util.ArrayList;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/17
 * <br /> Description :
 */
public class SleepData {

    /**
     * 横轴文字
     */
    private ArrayList<String> horizontalAxisTex = new ArrayList<>();

    /**
     * 画横线的高度比例，max为10
     */
    private ArrayList<SleepVerticalAxis> verticalPercentage = new ArrayList<>();


    /**
     * 数据列表
     */
    private ArrayList<SleepBean> sleepBeans = new ArrayList<>();

    public ArrayList<String> getHorizontalAxisTex() {
        return horizontalAxisTex;
    }

    public void setHorizontalAxisTex(ArrayList<String> horizontalAxisTex) {
        this.horizontalAxisTex = horizontalAxisTex;
    }


    public ArrayList<SleepBean> getSleepBeans() {
        return sleepBeans;
    }

    public void setSleepBeans(ArrayList<SleepBean> sleepBeans) {
        this.sleepBeans = sleepBeans;
    }

    public ArrayList<SleepVerticalAxis> getVerticalPercentage() {
        return verticalPercentage;
    }

    public void setVerticalPercentage(ArrayList<SleepVerticalAxis> verticalPercentage) {
        this.verticalPercentage = verticalPercentage;
    }
}
