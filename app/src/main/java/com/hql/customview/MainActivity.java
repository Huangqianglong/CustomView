package com.hql.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hql.customview.chart.BarCharts;
import com.hql.customview.chart.DataBean;
import com.hql.customview.chart.HorizontalAxisBean;
import com.hql.customview.heartbeat.CurveView;
import com.hql.customview.sleepChart.SleepCharBar;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BarCharts mBarCharts;
    SleepCharBar mOrientationBar;
    CurveView mCurveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBarCharts = findViewById(R.id.barchart);
        mOrientationBar = findViewById(R.id.origBar);
        mCurveView = findViewById(R.id.cureView);
        //initData();
    }

    public void onCharBar(View view) {
        Intent intent = new Intent(this,BarActivity.class);
        intent.setAction("1");
        startActivity(intent);
    }

    public void onSleepBar(View view) {
        Intent intent = new Intent(this,BarActivity.class);
        intent.setAction("2");
        startActivity(intent);
    }

    public void onCurveBar(View view) {
        Intent intent = new Intent(this,BarActivity.class);
        intent.setAction("3");
        startActivity(intent);
    }


    private void initData() {
        //柱形图数据
        ArrayList<HorizontalAxisBean> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 31; i++) {
            HorizontalAxisBean bean = new HorizontalAxisBean("12月" + (10 + i) + "日", random.nextInt(10));
            date.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            time.add(i * 3 + "");
        }

        DataBean dataBean = new DataBean(date, time);
        dataBean.setVerticalMaxData(5 * 3);

        mBarCharts.setPartition(4, 25);
        mBarCharts.setShowHorizontalAxisText(false);
        mBarCharts.setPaddingBarLeft(143);
        mBarCharts.setPaddingBarRight(100);
        mBarCharts.setCharWidth(18);
        mBarCharts.setMulticoloured(true, new int[]{2, 5, 3}, new int[]{0xff4E61CA, 0xff35458D, 0xff1E2653});
        mBarCharts.setDataBean(dataBean);


        //睡眠时段图数据
//        ArrayList<SleepBean> list = new ArrayList<>();
//        int all = 100;
//        Random random = new Random();
//        int i = 0;
//        ArrayList<String> typeList = new ArrayList<>();
//        typeList.add(SleepBean.TYPE_SLEEP_DEEP);
//        typeList.add(SleepBean.TYPE_SLEEP_SHALLOW);
//        typeList.add(SleepBean.TYPE_SLEEP_SOBER);
//        while (all > 0) {
//            i++;
//            if (i >= typeList.size()) {
//                i = 0;
//            }
//            int persent = random.nextInt(20);
//            SleepBean sleepBean;
//            if (all - persent > 0) {
//                sleepBean = new SleepBean(persent, typeList.get(i));
//            } else {
//                persent =  all;
//                Log.d("hql","添加  all："+all);
//                sleepBean = new SleepBean(persent, typeList.get(i));
//                all = 0;
//            }
//            all = all - persent;
//            Log.d("hql","添加："+sleepBean.getPercent());
//            list.add(sleepBean);
//
//        }
//
//        SleepData dataBean = new SleepData();
//        ArrayList<SleepVerticalAxis> oList = new ArrayList<>();
//        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_DEEP, "深度睡眠", 4));
//        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_SHALLOW, "浅度睡眠", 4));
//        oList.add(new SleepVerticalAxis(SleepBean.TYPE_SLEEP_SOBER, "清醒", 2));
//        dataBean.setVerticalPercentage(oList);
//        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_DEEP,0xff1D2649);
//        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_SHALLOW,0xff35448F);
//        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_SOBER,0xff4C62CD);
//
//
//        ArrayList<String> horizontalAxisTex = new ArrayList<>();
//        horizontalAxisTex.add("00:00");
//        horizontalAxisTex.add("02:00");
//        horizontalAxisTex.add("04:00");
//        horizontalAxisTex.add("06:00");
//        horizontalAxisTex.add("08:00");
//
//        dataBean.setHorizontalAxisTex(horizontalAxisTex);
//        dataBean.setSleepBeans(list);
//        mOrientationBar.setDataBean(dataBean);

        //波形图数据

//        ArrayList<String> horizontalAxisTex = new ArrayList<>();
//        horizontalAxisTex.add("00:00");
//        horizontalAxisTex.add("02:00");
//        horizontalAxisTex.add("04:00");
//        horizontalAxisTex.add("06:00");
//        horizontalAxisTex.add("08:00");
//        ArrayList<String> portraitAxisTex = new ArrayList<>();
//        portraitAxisTex.add("40");
//        portraitAxisTex.add("85");
//        portraitAxisTex.add("130");
//        portraitAxisTex.add("175");
//        portraitAxisTex.add("220");
//        CurveData curveData = new CurveData();
//        curveData.setHorizontalAxisTex(horizontalAxisTex);
//        curveData.setVerticalAxisTex(portraitAxisTex);
//
//        ArrayList<Integer> hearBeat = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 20; i++) {
//            hearBeat.add(random.nextInt(200));
//        }
//        curveData.setMaxData(220f);
//        curveData.setHeartBeatData(hearBeat);
//
//        mCurveView.setData(curveData);
    }


}