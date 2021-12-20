package com.hql.customview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hql.customview.chart.BarCharts;
import com.hql.customview.chart.DataBean;
import com.hql.customview.chart.HorizontalAxisBean;
import com.hql.customview.heartbeat.CurveData;
import com.hql.customview.heartbeat.CurveView;
import com.hql.customview.heartbeat.HeartBeatBean;
import com.hql.customview.sleepChart.SleepBean;
import com.hql.customview.sleepChart.SleepCharBar;
import com.hql.customview.sleepChart.SleepData;
import com.hql.customview.sleepChart.SleepVerticalAxis;
import com.hql.uitls.LoggerUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/23
 * <br /> Description :
 */
public class BarActivity extends AppCompatActivity {
    private static final int CHART_NORMAL_COLOR_1 = Color.parseColor("#146B7C");
    private static final int CHART_NORMAL_COLOR_2 = Color.parseColor("#1B2238");
    private static final int CHART_DAY_SELECT_COLOR_1 = Color.parseColor("#146B7C");
    private static final int CHART_DAY_SELECT_COLOR_2 = Color.parseColor("#1B2238");

    private static final int CHART_WEEK_SELECT_COLOR_1 = Color.parseColor("#FF4D4D");
    private static final int CHART_WEEK_SELECT_COLOR_2 = Color.parseColor("#1B2238");

    private static final int CHART_MONTH_SELECT_COLOR_1 = Color.parseColor("#F5B240");
    private static final int CHART_MONTH_SELECT_COLOR_2 = Color.parseColor("#1B2238");

    private static final int CHART_WEEK_WIDTH = 41;
    private static final int CHART_MONTH_WIDTH = 21;
    /**
     * 月，分组
     */
    private static final int CHART_MONTH_PARTITION = 4;
    /**
     * 月，分组距离
     */
    private static final int CHART_MONTH_PARTITION_DISTANCE = 25;
    private final static String TAG = "BarActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        String action = getIntent().getAction();
        switch (action){
            case "1":
                showChar();
                break;
            case "2":
                showSleep();
                break;
            case "3":
                showCurve();
                break;
            default:
                LoggerUtil.d(TAG,"啥也没选");
                break;
        }
    }
    private void showChar(){
        BarCharts barCharts = findViewById(R.id.bar);
        barCharts.setVisibility(View.VISIBLE);
        barCharts.setPaddingBarLeft(43);
        barCharts.setPaddingBarRight(43);
        barCharts.setOnItemSelectListener(barItemSelectListener);
        barCharts.setBindWithHorizontalData(true);
        barCharts.setShaderNormalColor(CHART_NORMAL_COLOR_1, CHART_NORMAL_COLOR_2);
        barCharts.setShaderSelectColor(CHART_MONTH_SELECT_COLOR_1, CHART_MONTH_SELECT_COLOR_2);
        barCharts.setCharWidth(CHART_MONTH_WIDTH);
        barCharts.setShowHorizontalAxisText(false);
        barCharts.setShowClickHighlightText(true);



        ArrayList<HorizontalAxisBean> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 31; i++) {
            HorizontalAxisBean bean = new HorizontalAxisBean("12月" +(i + 1)  + "日", random.nextInt(20) + 80);

            bean.setLabState(random.nextInt(2)+1);
            date.add(bean);
        }
        for (int i = 0; i < 3; i++) {
            time.add(90 + i * 5 + "%");
        }

        DataBean dataBean = new DataBean(date, time);
        dataBean.setVerticalMaxData(100);
        LoggerUtil.d(TAG, "发送数据：");


        LoggerUtil.d(TAG, "getUpdateMonthDataEvent 接收数据：" + dataBean);
        barCharts.setVisibility(View.VISIBLE);
        barCharts.setPartition(CHART_MONTH_PARTITION, CHART_MONTH_PARTITION_DISTANCE);
        barCharts.setDataBean(dataBean);
        barCharts.setPositionSelect(dataBean.getHorizontalAxisData().size()-1);
    }
    private void showSleep(){
        SleepCharBar sleepBar = findViewById(R.id.sleep_bar);
        sleepBar.setVisibility(View.VISIBLE);
        sleepBar.addPaint(SleepBean.TYPE_SLEEP_DEEP, Color.parseColor("#1D2649"));
        sleepBar.addPaint(SleepBean.TYPE_SLEEP_SHALLOW, Color.parseColor("#35448F"));
        sleepBar.addPaint(SleepBean.TYPE_SLEEP_SOBER, Color.parseColor("#4C62CD"));
      
        sleepBar.setOnItemSelectListener(barItemSelectListener);

        ArrayList<SleepBean> list = new ArrayList<>();
        int all = 100;
        Random random = new Random();
        int i = 0;
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add(SleepBean.TYPE_SLEEP_DEEP);
        typeList.add(SleepBean.TYPE_SLEEP_SHALLOW);
        typeList.add(SleepBean.TYPE_SLEEP_SOBER);
        while (all > 0) {
            i++;
            if (i >= typeList.size()) {
                i = 0;
            }
            int persent = random.nextInt(20);
            SleepBean sleepBean;
            if (all - persent > 0) {
                sleepBean = new SleepBean(persent, typeList.get(i));
            } else {
                persent = all;
                Log.d("hql", "添加  all：" + all);
                sleepBean = new SleepBean(persent, typeList.get(i));
                all = 0;
            }
            all = all - persent;
            Log.d("hql", "添加：" + sleepBean.getPercent());
            sleepBean.setDate("2021年12月23日");

            list.add(sleepBean);

        }

        SleepData dataBean = new SleepData();
        ArrayList<SleepVerticalAxis> oList = new ArrayList<>();
        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_DEEP, "深度睡眠", 4));
        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_SHALLOW, "浅度睡眠", 4));
        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_SOBER, "清醒", 2));
        dataBean.setVerticalPercentage(oList);


        ArrayList<String> horizontalAxisTex = new ArrayList<>();
        horizontalAxisTex.add("00:00");
        horizontalAxisTex.add("02:00");
        horizontalAxisTex.add("04:00");
        horizontalAxisTex.add("06:00");
        horizontalAxisTex.add("08:00");

        dataBean.setHorizontalAxisTex(horizontalAxisTex);
        dataBean.setSleepBeans(list);
        int min = (random.nextInt(6) + 6) * 60 + random.nextInt(59);
        //LoggerUtil.d(TAG,"设置时间 "+min);
        dataBean.setMaxData(min);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        String date =cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) - 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        dataBean.setDate(date);

        sleepBar.setDataBean(dataBean);

    }
    private void showCurve(){
        CurveView curveView = findViewById(R.id.cureView);
        curveView.setVisibility(View.VISIBLE);

        curveView.setOnItemSelectListener(barItemSelectListener);
        ArrayList<String> horizontalAxisTex = new ArrayList<>();
        horizontalAxisTex.add("00:00");
        horizontalAxisTex.add("06:00");
        horizontalAxisTex.add("12:00");
        horizontalAxisTex.add("18:00");
        horizontalAxisTex.add("24:00");
        ArrayList<String> portraitAxisTex = new ArrayList<>();
        portraitAxisTex.add("40");
        portraitAxisTex.add("85");
        portraitAxisTex.add("130");
        portraitAxisTex.add("175");
        portraitAxisTex.add("220");
        CurveData curveData = new CurveData();
        curveData.setHorizontalAxisTex(horizontalAxisTex);
        curveData.setVerticalAxisTex(portraitAxisTex);

        ArrayList<HeartBeatBean> hearBeat = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            HeartBeatBean bean = new HeartBeatBean();
            bean.setRate(60 + random.nextInt(40));
            Date date = new Date(System.currentTimeMillis() + 1000 * 60 * i);
            String dateStr = simpleDateFormat.format(date);
            bean.setTime(dateStr);
            hearBeat.add(bean);
        }
        curveData.setMaxData(220f);
        curveData.setHeartBeatData(hearBeat);
        curveView.setData(curveData);
    }
    OnItemSelectListener barItemSelectListener = new OnItemSelectListener() {
        @Override
        public void onItemSelect(int index) {
            LoggerUtil.d(TAG, "onItemSelect " + index);
        }
    };
}
