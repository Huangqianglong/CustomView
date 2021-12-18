package com.hql.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/17
 * <br /> Description :
 */
public class OrientationBar extends View {
    private static final String TAG = "OrientationBar";
    /**
     * 宽
     */
    private int mViewWidth;
    /**
     * 高
     */
    private int mViewHeight;
    /**
     * 数据集
     */
    private SleepData mDataBean;
    /**
     * X轴主线的起点坐标Y，即原点坐标Y
     */
    float mAxisStartY;
    /**
     * X轴主线的结束坐标，即原点坐标X
     */
    float mAxisEndX;

    /**
     * 横轴文字画笔
     */
    private Paint horizontalTextPaint;

    /**
     * 横轴背景线画笔
     */
    private Paint horizontalLinePaint;

    /**
     * 横轴背景线颜色
     */
    private int horizontalLineColor = 0xff353A4B;
    /**
     * 纵轴文字画笔
     */
    private Paint verticalTextPaint;
    private Paint segmentPaint;

    public OrientationBar(Context context) {
        super(context);
        init();
    }

    public OrientationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrientationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OrientationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //横轴线
        horizontalLinePaint = new Paint();
        //horizontalLinePaint.setColor(horizontalLineColor);
        horizontalLinePaint.setColor(Color.WHITE);
        horizontalLinePaint.setAntiAlias(true);
        //横轴文字
        horizontalTextPaint = new Paint();
        horizontalTextPaint.setColor(Color.WHITE);
        horizontalTextPaint.setAntiAlias(true);
        horizontalTextPaint.setTextSize(28f);

        //纵轴文字
        verticalTextPaint = new Paint();
        verticalTextPaint.setColor(Color.WHITE);
        verticalTextPaint.setAntiAlias(true);
        verticalTextPaint.setTextSize(28f);

        segmentPaint= new Paint();
        segmentPaint.setColor(Color.WHITE);
        segmentPaint.setAntiAlias(true);
        segmentPaint.setTextSize(28f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int w = ViewUtils.measureDimension(200, widthMeasureSpec);
        int h = ViewUtils.measureDimension(80, heightMeasureSpec);
        setMeasuredDimension(w, h);
        Log.d(TAG, ">>onMeasure  w:" + w + ">>h:" + h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, ">>onSizeChanged");
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBG(canvas);
        drawHorizontalAxis(canvas);
    }


    /**
     * 1。先画坐标轴
     * 2。绘制分区，同时计算保存每个type的y1,y2用于绘制数据的矩形
     * 3。绘制横轴文字
     * 4。绘制数据
     * @param canvas
     */
    private void drawHorizontalAxis(Canvas canvas) {
        //计算原点
        float height = 0;
        if (mAxisStartY == 0) {
            height = mViewHeight - (18 + ViewUtils.getLineHeight(horizontalTextPaint));
            mAxisStartY = height;
            Log.d(TAG, ">>>>mViewHeight:" + mViewHeight + ">>mAxisStartY:" + mAxisStartY);
        } else {
            height = mAxisStartY;
        }


        float endX = 0;
        if (mAxisEndX == 0) {
            float textWidth = 60 + horizontalTextPaint.measureText(mDataBean.getHorizontalAxisTex().get(0)) + 5;
            mAxisEndX = endX = mViewWidth - textWidth;
        } else {
            endX = mAxisEndX;
        }


        //画纵向的几个横线
        float interval = mAxisStartY / mDataBean.getVerticalPercentage().size();
        canvas.drawLine(0, height, endX, height, horizontalLinePaint);
        //记录对应type的y高度
        HashMap<String, Ycoordinate> YMap = new HashMap<>();
        ArrayList<SleepVerticalAxis> verticalPercentage = mDataBean.getVerticalPercentage();
        if (verticalPercentage.size() > 0) {
            canvas.drawText(verticalPercentage.get(0).getText(), mAxisEndX, mAxisStartY, verticalTextPaint);
            //按高度比例画横线
            int size = verticalPercentage.size();

            for (int j = 0; j < size; j++) {
                Ycoordinate ycoordinate = new Ycoordinate();
                if (j == 0) {
                    ycoordinate.yLow = (int) mAxisStartY;
                } else {
                    ycoordinate.yLow = (int) height;
                }
                height = height - mAxisStartY * verticalPercentage.get(j).getPercent() / 10;
                ycoordinate.yHeight = (int) height;
                canvas.drawLine(0, height, endX, height, horizontalLinePaint);

                if (size > j + 1) {
                    //Log.d(TAG, "按比例画横线：" + height + ">>" + verticalPercentage.get(j + 1).getText());
                    canvas.drawText(verticalPercentage.get(j + 1).getText(), mAxisEndX + 15, height, verticalTextPaint);
                }
                //Log.d(TAG, "保存类型：" + verticalPercentage.get(j).getType()+">("+ycoordinate.yLow+","+ycoordinate.yHeight+")");

                YMap.put(verticalPercentage.get(j).getType(), ycoordinate);
            }
            Log.d(TAG,"总高度:"+mAxisStartY);

        }


        ArrayList<String> horizontalTextData = mDataBean.getHorizontalAxisTex();
        int textWidth = 0;
        if (0 != horizontalTextData.size()) {
            textWidth = (int) horizontalTextPaint.measureText(horizontalTextData.get(0));
            Log.d(TAG, "文字宽度" + textWidth);
        }
        float textStartX = 0;
        float horizontalInterval = (mAxisEndX - textWidth * horizontalTextData.size()) / (horizontalTextData.size() - 1);
        Log.d(TAG, "宽度 " + horizontalInterval + ">>" + (mAxisEndX - textWidth * horizontalTextData.size())
                + ">>" + (horizontalTextData.size() - 1)
        );
        for (int i = 0; i < horizontalTextData.size(); i++) {
            //画X轴文字
            canvas.drawText(horizontalTextData.get(i),
                    textStartX + horizontalInterval * i,
                    mViewHeight - 10,
                    horizontalTextPaint
            );
            textStartX += textWidth;

            //画横向数据

        }
        ArrayList<SleepBean> sleepBeans = mDataBean.getSleepBeans();
        int valueStartX = 0;
        int percent = 0;
        for (int i = 0; i < sleepBeans.size(); i++) {
            String type = sleepBeans.get(i).getType();
            Ycoordinate ycoordinate = YMap.get(type);
            percent=percent +sleepBeans.get(i).getPercent();
            Rect rect = new Rect(valueStartX,
                    ycoordinate.yHeight,
                    valueStartX+(int)(sleepBeans.get(i).getPercent() /100f* mAxisEndX),
                    ycoordinate.yLow);
            segmentPaint.setColor(colorPaintMap.get(type));
            canvas.drawRect(
                    rect,
                    segmentPaint

            );
            valueStartX += (int)(sleepBeans.get(i).getPercent() /100f* mAxisEndX);
            }


    }

    private void drawBG(Canvas canvas) {
    }

    public void setDataBean(SleepData dataBean) {
        this.mDataBean = dataBean;
        Log.d(TAG, "setDataBean");
        this.invalidate();
    }

    private class Ycoordinate {
        int yLow;
        int yHeight;

    }
    HashMap<String,Integer> colorPaintMap = new HashMap<>();
    public void addPaint(String type,int color){
        colorPaintMap.put(type,color);
    }
}
