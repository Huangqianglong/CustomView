package com.hql.customview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BarCharts mBarCharts;
    OrientationBar mOrientationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBarCharts = findViewById(R.id.barchart);
        mOrientationBar = findViewById(R.id.origBar);
        initData();
    }

    private void initData() {
//        ArrayList<HorizontalAxisBean> date = new ArrayList<>();
//        ArrayList<String> time = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i <31; i++) {
//            HorizontalAxisBean bean = new HorizontalAxisBean("12月"+(10+i)+"日",random.nextInt(10));
//            date.add(bean);
//        }
//        for (int i = 0; i < 5; i++) {
//            time.add(i*3+"");
//        }
//
//        DataBean dataBean = new DataBean(date,time);
//        dataBean.setVerticalMaxData(5*3);

//        mBarCharts.setPartition(4,25);
//        mBarCharts.setShowHorizontalAxisText(false);
//        mBarCharts.setPaddingBarLeft(43);
//        mBarCharts.setCharWidth(18);
//        mBarCharts.setMulticoloured(true,new int[]{2,5,3},new int []{0xff4E61CA,0xff35458D,0xff1E2653});
//        mBarCharts.setDataBean(dataBean);


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
                persent =  all;
                Log.d("hql","添加  all："+all);
                sleepBean = new SleepBean(persent, typeList.get(i));
                all = 0;
            }
            all = all - persent;
            Log.d("hql","添加："+sleepBean.getPercent());
            list.add(sleepBean);

        }

        SleepData dataBean = new SleepData();
        ArrayList<SleepHorizontalAxis> oList = new ArrayList<>();
        oList.add(new SleepHorizontalAxis(SleepBean.TYPE_SLEEP_DEEP, "深度睡眠", 4));
        oList.add(new SleepHorizontalAxis(SleepBean.TYPE_SLEEP_SHALLOW, "浅度睡眠", 4));
        oList.add(new SleepHorizontalAxis(SleepBean.TYPE_SLEEP_SOBER, "清醒", 2));
        dataBean.setVerticalPercentage(oList);
        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_DEEP,0xff993344);
        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_SHALLOW,0xff663344);
        mOrientationBar.addPaint(SleepBean.TYPE_SLEEP_SOBER,0xff229944);


        ArrayList<String> horizontalAxisTex = new ArrayList<>();
        horizontalAxisTex.add("00:00");
        horizontalAxisTex.add("02:00");
        horizontalAxisTex.add("04:00");
        horizontalAxisTex.add("06:00");
        horizontalAxisTex.add("08:00");

        dataBean.setHorizontalAxisTex(horizontalAxisTex);
        dataBean.setSleepBeans(list);
        mOrientationBar.setDataBean(dataBean);

    }
}