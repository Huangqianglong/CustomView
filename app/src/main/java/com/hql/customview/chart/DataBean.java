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

    /**
     * 纵轴文字
     */
    private ArrayList<String> mVerticalAxisData;
    /**
     * 不绑定的横轴文字
     */
    private ArrayList<String> mOnlyHorizontalAxisText = new ArrayList<>();


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

    public void setHorizontalAxisTextBind(ArrayList<String> texts) {

        mOnlyHorizontalAxisText.clear();
        if (null != texts) {
            mOnlyHorizontalAxisText.addAll(texts);
        }
    }


    public ArrayList<String> getOnlyHorizontalAxisText() {
        return mOnlyHorizontalAxisText;
    }
}
