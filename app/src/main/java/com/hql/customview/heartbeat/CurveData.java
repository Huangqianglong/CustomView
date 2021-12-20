package com.hql.customview.heartbeat;

import java.util.ArrayList;

/**
 * /**
 *
 * @Author: Huangql
 * @CreateDate: 21-12-19 下午3:24
 * @UpdateDate: 21-12-19 下午3:24
 * @Description: java类作用描述
 */
public class CurveData {
    /**
     * 横轴文字
     */
    private ArrayList<String> horizontalAxisTex = new ArrayList<>();

    /**
     * 纵轴文字
     */
    private ArrayList<String> verticalAxisTex = new ArrayList<>();

    private ArrayList<HeartBeatBean> heartBeatData = new ArrayList<>();
    private float maxData;

    public ArrayList<String> getHorizontalAxisTex() {
        return horizontalAxisTex;
    }

    public void setHorizontalAxisTex(ArrayList<String> horizontalAxisTex) {
        this.horizontalAxisTex = horizontalAxisTex;
    }

    public void setVerticalAxisTex(ArrayList<String> verticalAxisTex) {
        this.verticalAxisTex = verticalAxisTex;
    }

    public ArrayList<String> getVerticalAxisTex() {
        return verticalAxisTex;
    }

    public ArrayList<HeartBeatBean> getHeartBeatData() {
        return heartBeatData;
    }

    public void setHeartBeatData(ArrayList<HeartBeatBean> heartBeatData) {
        this.heartBeatData = heartBeatData;
    }

    public float getMaxData() {
        return maxData;
    }

    public void setMaxData(float maxData) {
        this.maxData = maxData;
    }
}
