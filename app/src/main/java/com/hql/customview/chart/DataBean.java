package com.hql.customview.chart;

import java.util.ArrayList;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/15
 * <br /> Description :
 */
public class DataBean {
    /**
     * 纵坐标最大值
     */
    private int verticalMaxData = 0;
    /**
     * 纵坐标最小值
     */
    private int verticalMinData = 0;
    /**
     * 横轴数据
     */
    private ArrayList<HorizontalAxisBean> mHorizontalAxisData;
    private ArrayList<String> mVerticalAxisData;

    public DataBean(ArrayList<HorizontalAxisBean> mHorizontalAxisData, ArrayList<String> mVerticalAxisData) {
        this.mHorizontalAxisData = mHorizontalAxisData;
        this.mVerticalAxisData = mVerticalAxisData;
    }

    public ArrayList<HorizontalAxisBean> getHorizontalAxisData() {
        return mHorizontalAxisData;
    }

    public void setHorizontalAxisData(ArrayList<HorizontalAxisBean> horizontalAxisData) {
        this.mHorizontalAxisData = horizontalAxisData;
    }

    public ArrayList<String> getVerticalAxisData() {
        return mVerticalAxisData;
    }

    public void setVerticalAxisData(ArrayList<String> verticalAxisData) {
        this.mVerticalAxisData = verticalAxisData;
    }

    public int getVerticalMaxData() {
        return verticalMaxData;
    }

    public void setVerticalMaxData(int verticalMaxData) {
        this.verticalMaxData = verticalMaxData;
    }

    public int getVerticalMinData() {
        return verticalMinData;
    }

    public void setVerticalMinData(int verticalMinData) {
        this.verticalMinData = verticalMinData;
    }
}
