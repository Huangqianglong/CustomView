package com.hql.customview.chart;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.hql.customview.OnItemSelectListener;
import com.hql.uitls.LoggerUtil;
import com.hql.uitls.ViewUtils;

import java.util.ArrayList;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/15
 * <br /> Description : 柱形图
 */
public class BarCharts extends View {
    boolean DEBUG = false;
    private static final String TAG = "BarCharts";
    /**
     * bar的全部数据
     */
    private DataBean mDataBean;
    /**
     * 宽
     */
    private int mViewWidth;
    /**
     * 高
     */
    private int mViewHeight;

    /**
     * 柱形图背景色
     */
    private int barSecondColor = 0xFF222539;

    /**
     * 横轴文字大小
     */
    private int horizontalTextSize;
    /**
     * 纵轴轴文字大小
     */
    private int verticalTextSize;
    /**
     * 横轴背景线画笔
     */
    private Paint horizontalLinePaint;
    /**
     * 横轴文字画笔
     */
    private Paint horizontalTextPaint;
    /**
     * 纵轴文字画笔
     */
    private Paint verticalTextPaint;
    /**
     * 横轴虚线画笔
     */
    private Paint horizontalDottedPaint;
    /**
     * 虚线颜色
     */
    private int dottedColor = 0xff2FCAE8;

    /**
     * 设置横轴背景线颜色
     *
     * @param horizontalLineColor
     */
    public void setHorizontalLineColor(int horizontalLineColor) {
        this.horizontalLineColor = horizontalLineColor;
    }

    /**
     * 横轴背景线颜色
     */
    private int horizontalLineColor = 0xff353A4B;

    /**
     * 柱形图背景笔
     */
    private Paint mCharBGPaint;
    /**
     * 柱形图笔
     */
    private Paint mCharPaint;

    /**
     * 左侧柱形图偏移起点量
     */
    private int paddingBarLeft = 15;
    private int paddingBarRight = 0;

    private int paddingRight = 0;

    /**
     * 渐变色 - normal
     */
    private int shaderColorNormalStart = 0xff146B7C;
    private int shaderColorNormalEnd = 0xff1B2238;

    /**
     * 发光范围
     */
    int radioRadius = 13;

    /**
     * 光晕画笔
     */
    private Paint outShadowPaint;
    /**
     * 渐变色 - select
     */
    private int shaderColorSelectStart = 0xff2FCAE8;
    private int shaderColorSelectEnd = 0xff1B2238;
    /**
     * 分组数
     */
    private int mPartition;
    /**
     * 分组间隔距离
     */
    private int mGroupDistance;
    /**
     * X轴主线的起点坐标
     */
    float mAxisStartY;
    /**
     * X轴主线的结束坐标
     */
    float mAxisEndX;

    /**
     * 是否画横轴的文字
     */
    private boolean drawHorizontalAxisText = true;


    /**
     * 柱形图宽
     */
    private int mCharWidth = 15;
    /**
     * 响应区间的一半
     */
    private float halfResponseWith;

    /**
     * 渐变处理
     */
    private LinearGradient mShader;
    /**
     * 柱形图背景Rect
     */
    private ArrayList<Rect> mCharBGRectList = new ArrayList<>();
    /**
     * 柱形图Rect
     */
    private ArrayList<Rect> mCharRectList = new ArrayList<>();
    /**
     * 点击响应
     */
    private ArrayList<Rect> mClickRectList = new ArrayList<>();

    /**
     * 横轴文字区域
     */
    private ArrayList<Rect> mHorizontalTextList = new ArrayList<>();
    /**
     * 是否绘制高亮状态
     */
    boolean isDrawHighlight = false;
    /**
     * 选中高亮的index
     */
    private int mHighlightIndex;
    /**
     * 分段比例
     */
    private int[] mProportion;
    /**
     * 分段比例
     */
    private int[] mMultiColors;
    /**
     * 是否颜色分段
     */
    private boolean isOpenMulti;

    /**
     * 底部文字距离
     */
    private int bottomTextMargin = 10;
    /**
     * 横向文字是否和数据绑定
     */
    private boolean bindWithHorizontalData = true;
    /**
     * 没有数据时，是否画柱形背景
     */
    private boolean drawCharBGNoData = true;

    public BarCharts(Context context) {
        super(context);
        init();
    }

    public BarCharts(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarCharts(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BarCharts(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Log.d(TAG, "init()");
        //横轴线
        horizontalLinePaint = new Paint();
        horizontalLinePaint.setColor(horizontalLineColor);
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

        mCharBGPaint = new Paint();
        //柱形图背景颜色
        mCharBGPaint.setColor(horizontalLineColor);
        mCharBGPaint.setAntiAlias(true);
        mCharPaint = new Paint();
        //柱形图颜色
        mCharPaint.setColor(0xff4C62CA);
        mCharPaint.setAntiAlias(true);

        //虚线
        horizontalDottedPaint = new Paint();
        horizontalDottedPaint.setColor(dottedColor);
        horizontalDottedPaint.setAntiAlias(true);
        horizontalDottedPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
        //光晕画笔
        outShadowPaint = new Paint();
        outShadowPaint.setColor(Color.WHITE);
        outShadowPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.OUTER));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw start");


        drawBG(canvas);
        drawHorizontalAxis(canvas);
        drawVerticalAxis(canvas);

        if (isDrawHighlight) {
            drawHighlightChart(canvas);
            isDrawHighlight = false;
        }
        Log.d(TAG, "onDraw  end");
        //mCanvas.drawText("测试",50,50,verticalTextPaint);
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawBG(Canvas canvas) {
    }


    /**
     * 画横轴
     *
     * @param canvas
     */
    private void drawHorizontalAxis(Canvas canvas) {
        Log.d(TAG, "drawHorizontalAxis");
        if (null == mDataBean || (mDataBean.getVerticalAxisData().size() < 1 || mDataBean.getHorizontalAxisData().size() < 1)) {
            return;
        }
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
            float textWidth = 60 + horizontalTextPaint.measureText(mDataBean.getVerticalAxisData().get(mDataBean.getVerticalAxisData().size() - 1)) + 5;
            mAxisEndX = endX = mViewWidth - textWidth - paddingRight;
        } else {
            endX = mAxisEndX;
        }

        //画纵向的几个横线
        float interval = mAxisStartY / (mDataBean.getVerticalAxisData().size() - 1);
        if (mDataBean.getVerticalAxisData().size() == 0) {
            canvas.drawLine(0, height, endX, height, horizontalLinePaint);
        } else {
            while (height > 0) {
                canvas.drawLine(0, height, endX, height, horizontalLinePaint);
                height = height - interval;
                if (height <= 0) {
                    height = 1;
                    canvas.drawLine(0, height, endX, height, horizontalLinePaint);
                    break;
                }
            }
        }
        //画X轴数据
        ArrayList<HorizontalAxisBean> horizontalData = mDataBean.getHorizontalAxisData();
        float startX = 0;
        //每个柱形图的响应区间大小
        interval = (mAxisEndX - mPartition * mGroupDistance - paddingBarLeft - paddingBarRight) / horizontalData.size();
        //响应区间的一半
        halfResponseWith = interval / 2;
        //分组
        int currentPartition = 0;
        for (int i = 0; i < horizontalData.size(); i++) {
            //计算分组时 x的位移,当前i属于第几组，是否需要添加偏移
            if (mPartition > 0) {
                while (currentPartition < i / (horizontalData.size() / mPartition)) {
                    currentPartition += 1;
                    startX += mGroupDistance;
                }
            }


            //画柱形图背景
            Rect rect = null;
            if (i > mCharBGRectList.size() - 1) {
                rect = new Rect((int) (paddingBarLeft + startX + halfResponseWith - mCharWidth / 2),
                        0,
                        (int) (paddingBarLeft + startX + halfResponseWith + mCharWidth / 2),
                        (int) mAxisStartY);
                mCharBGRectList.add(rect);
            } else {
                rect = mCharBGRectList.get(i);
            }

            //没有数据时，是否画柱形图的背景
            //LoggerUtil.d(TAG, "drawCharBGNoData :" + drawCharBGNoData + ">>>value:" + drawCharBGNoData);
            if (!drawCharBGNoData) {
                if (horizontalData.get(i).getValue() != 0) {
                    canvas.drawRect(rect, mCharBGPaint);
                }
            } else {
                canvas.drawRect(rect, mCharBGPaint);
            }

            //画柱形图
            //柱形
            Rect rectChart = null;
            if (i > mCharRectList.size() - 1) {
                rectChart = new Rect((int) (paddingBarLeft + startX + halfResponseWith - mCharWidth / 2),
                        (int) (mAxisStartY - horizontalData.get(i).getValue() / mDataBean.getVerticalMaxData() * mAxisStartY),
                        (int) (paddingBarLeft + startX + halfResponseWith + mCharWidth / 2),
                        (int) mAxisStartY);
                mCharRectList.add(rectChart);
            } else {
                rectChart = mCharRectList.get(i);
            }
            if (isOpenMulti) {
                //分段色
                drawMulti(canvas, rectChart, i);
            } else {
                //渐变色
                mShader = new LinearGradient(paddingBarLeft + startX + halfResponseWith - mCharWidth / 2,
                        //按比例计算高度
                        mAxisStartY - horizontalData.get(i).getValue() / mDataBean.getVerticalMaxData() * mAxisStartY,
                        paddingBarLeft + startX + halfResponseWith + mCharWidth / 2,
                        mAxisStartY, shaderColorNormalStart, shaderColorNormalEnd, Shader.TileMode.CLAMP);
                mCharPaint.setShader(mShader);
                canvas.drawRect(rectChart, mCharPaint);
            }
            //画横轴文字
            //是否画横轴的文字

            if (bindWithHorizontalData) {
                //记录横轴文字区域
                if (0 == i || i == horizontalData.size() - 1) {
                    if (mHorizontalTextList.size() < 2) {
                        float textWidth = horizontalTextPaint.measureText(horizontalData.get(i).getText());
                        float x = rectChart.left + mCharWidth / 2 - horizontalTextPaint.measureText(horizontalData.get(i).getText()) / 2;
                        Rect rectStr = new Rect((int) (x),
                                (int) (mViewHeight - ViewUtils.getLineHeight(horizontalTextPaint)),
                                (int) (x + textWidth),
                                mViewHeight - bottomTextMargin + 5);
                        mHorizontalTextList.add(rectStr);
                    }
                }
                //LoggerUtil.d(TAG, "记录横轴文字区域 size" + mHorizontalTextList.size());
                if (drawHorizontalAxisText) {
                    //横轴的文字是否和数据绑定显示
                    canvas.drawText(horizontalData.get(i).getText(),
                            rectChart.left + mCharWidth / 2 - horizontalTextPaint.measureText(horizontalData.get(i).getText()) / 2,
                            mViewHeight - bottomTextMargin,
                            horizontalTextPaint);
                } else {
                    if (i == 0 || i == horizontalData.size() - 1) {
                        canvas.drawText(horizontalData.get(i).getText(),
                                rectChart.left + mCharWidth / 2 - horizontalTextPaint.measureText(horizontalData.get(i).getText()) / 2,
                                mViewHeight - bottomTextMargin,
                                horizontalTextPaint);
                    }
                }
            }

            //点击监听
            Rect clickRect = null;
            if (i > mClickRectList.size() - 1) {
                clickRect = new Rect((int) (paddingBarLeft + startX), 0,
                        (int) (paddingBarLeft + startX + interval), (int) mAxisStartY);
                mClickRectList.add(clickRect);
            } else {
                clickRect = mClickRectList.get(i);
            }


            if (DEBUG) {
                canvas.drawText(i + "", paddingBarLeft + startX + halfResponseWith - mCharWidth / 2,
                        mAxisStartY - bottomTextMargin,
                        horizontalTextPaint);
                canvas.drawLine(paddingBarLeft + startX + interval, 0,
                        paddingBarLeft + startX + interval, mAxisStartY,
                        horizontalTextPaint);
                canvas.drawCircle(mAxisEndX, mAxisStartY, 13, horizontalLinePaint);
            }
            startX = startX + interval;
            //Log.d(TAG, "new startX :" + startX);
        }
        //绘制非绑定数据横轴文字
        if (!bindWithHorizontalData) {
            ArrayList<String> horizonText = mDataBean.getOnlyHorizontalAxisText();
            float textWith = horizontalTextPaint.measureText(horizonText.get(0));
            float textHeight = ViewUtils.getLineHeight(horizontalTextPaint);
            float wh = mAxisEndX - textWith * horizonText.size();
            float intervalText = wh / (horizonText.size() - 1);
            float intervalStart = 0;
            for (int i = 0; i < horizonText.size(); i++) {
                canvas.drawText(horizonText.get(i),
                        intervalStart + intervalText * i,
                        mAxisStartY + textHeight + 5,
                        horizontalTextPaint);
                intervalStart += textWith;
            }
        }
    }

    /**
     * 画分段
     *
     * @param canvas
     * @param rectChart
     */
    private void drawMulti(Canvas canvas, Rect rectChart, int position) {
        int top = rectChart.top;
        int bootom;
        int h = rectChart.bottom - rectChart.top;
        HorizontalAxisBean bean = mDataBean.getHorizontalAxisData().get(position);
        if (null != bean.getRatio()) {
            int[] ratio = bean.getRatio();
            for (int j = 0; j < mMultiColors.length; j++) {
                int increment = (int) (h * ratio[j] / 10f);
                bootom = (int) (top + increment);
                Rect r1 = new Rect(rectChart.left,
                        top,
                        rectChart.right,
                        bootom
                );
                mCharPaint.setColor(mMultiColors[j]);
                canvas.drawRect(r1, mCharPaint);
                //Log.d(TAG,"Top :（"+top+","+bootom+")"+"增量："+increment +">>>"+r1.toString());
                top = bootom;
            }
        } else {
            for (int j = 0; j < mMultiColors.length; j++) {
                int increment = (int) (h * mProportion[j] / 10f);
                bootom = (int) (top + increment);
                Rect r1 = new Rect(rectChart.left,
                        top,
                        rectChart.right,
                        bootom
                );
                mCharPaint.setColor(mMultiColors[j]);
                canvas.drawRect(r1, mCharPaint);
                //Log.d(TAG,"Top :（"+top+","+bootom+")"+"增量："+increment +">>>"+r1.toString());
                top = bootom;
            }
        }

    }


    /**
     * 画纵轴
     *
     * @param canvas
     */
    private void drawVerticalAxis(Canvas canvas) {
        if (null == mDataBean || (mDataBean.getVerticalAxisData().size() < 1 || mDataBean.getHorizontalAxisData().size() < 1)) {
            return;
        }
        float padding = horizontalTextPaint.measureText(mDataBean.getVerticalAxisData().get(mDataBean.getVerticalAxisData().size() - 1)) / 2 + 5;

        ArrayList<String> data = mDataBean.getVerticalAxisData();
        float startY = mAxisStartY;
        int size = data.size();
        float strHeight = ViewUtils.getLineHeight(verticalTextPaint);
        float interval = (mAxisStartY - size * strHeight) / (size - 1);
        for (int i = 0; i < data.size(); i++) {
            canvas.drawText(data.get(i), mAxisEndX + padding, startY, verticalTextPaint);
            //Log.d(TAG, "verticalText :" + data.get(i));

            startY = startY - interval - strHeight;
            if (startY <= 0) {
                startY += strHeight;
            }
        }


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
        mCharBGRectList.clear();
        mCharRectList.clear();
        mClickRectList.clear();
    }

    public DataBean getDataBean() {
        return mDataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.mDataBean = dataBean;
        Log.d(TAG, "setDataBean");
        mCharBGRectList.clear();
        mCharRectList.clear();
        mClickRectList.clear();
        mHorizontalTextList.clear();
        this.invalidate();
    }

    /**
     * 设置渐变色
     *
     * @param colorStart
     * @param colorStartEnd
     */
    public void setShaderNormalColor(int colorStart, int colorStartEnd) {
        shaderColorNormalStart = colorStart;
        shaderColorNormalEnd = colorStartEnd;
    }

    /**
     * 设置高亮渐变色
     *
     * @param colorStart
     * @param colorStartEnd
     */
    public void setShaderSelectColor(int colorStart, int colorStartEnd) {
        shaderColorSelectStart = colorStart;
        shaderColorSelectEnd = colorStartEnd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //Log.d(TAG, "ACTION_UP事件");
                if (x + getLeft() < getRight() && y + getTop() < getBottom() && x < mAxisEndX) {
                    Log.d(TAG, "点击事件 x:" + x);

                    for (int i = 0; i < mClickRectList.size(); i++) {
                        if (mClickRectList.get(i).contains(x, y)) {
                            onItemSelect(i);
                        }
                    }
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 判断某个值是否在某个区间内(区间闭合,包含所在的起始值和结束值)
     *
     * @param current 特定数值
     * @param min     区间起始位
     * @param max     区间结束位
     * @return true 在;false 不在
     */
    public boolean rangeInDefined(int index, int current, int min, int max) {
        Log.d(TAG, "index:" + index + ">>current :" + current + ">>> (" + min + "," + max + ")");
        return Math.max(min, current) == Math.min(current, max);
    }

    private void onItemSelect(int itemSelect) {
        Log.d(TAG, "选中 ：" + itemSelect);
        if (-1 != itemSelect) {
            drawHighlightChart(itemSelect);
            if (null != mItemSelectListener) {
                mItemSelectListener.onItemSelect(itemSelect);
            }
        }

    }


    private void drawHighlightChart(int position) {
        isDrawHighlight = true;
        mHighlightIndex = position;
        invalidate();
        //drawHighlightChart(mCanvas);
    }


    /**
     * 绘制高亮
     *
     * @param canvas
     */
    private void drawHighlightChart(Canvas canvas) {
        Log.d(TAG, "绘制高亮");
//每个柱形图的响应区间大小
        Rect rect = mCharRectList.get(mHighlightIndex);

//画虚线
        canvas.drawLine(rect.left,
                rect.top + 1,
                mAxisEndX,
                rect.top + 1,
                horizontalDottedPaint);
        //画柱形图

        if (isOpenMulti) {
            drawMulti(canvas, mCharRectList.get(mHighlightIndex), mHighlightIndex);
        } else {
            //柱形
            mShader = new LinearGradient(rect.left,
                    //按比例计算高度
                    rect.top,
                    rect.right,
                    rect.bottom, shaderColorSelectStart, shaderColorSelectEnd, Shader.TileMode.MIRROR);
            mCharPaint.setShader(mShader);
            canvas.drawRect(mCharRectList.get(mHighlightIndex),
                    mCharPaint);
        }

        //画光晕
        canvas.drawRect(rect, outShadowPaint);

        if (showHighlightText) {
            ArrayList<HorizontalAxisBean> horizontalData = mDataBean.getHorizontalAxisData();
            String text = horizontalData.get(mHighlightIndex).getText();
            float x = rect.left - horizontalTextPaint.measureText(text) / 2 + mCharWidth / 2;
            float y = mViewHeight - bottomTextMargin - 1;
            //判断压盖关系
            if (bindWithHorizontalData
                    && mHighlightIndex != 0
                    && mHighlightIndex != mDataBean.getHorizontalAxisData().size() - 1) {
                Rect clipRect = null;
                //是否覆盖了左侧
                if (mHorizontalTextList.get(0).contains((int) x, (int) y)) {
                    clipRect = mHorizontalTextList.get(0);
                }
                //是否覆盖了右侧
                LoggerUtil.d(TAG, "rect 1  :" + mHorizontalTextList.get(1) + ">>" + mHorizontalTextList.get(mHorizontalTextList.size() - 1).contains((int) x, (int) y));
                if (mHorizontalTextList.get(mHorizontalTextList.size() - 1).contains((int) x, (int) y)
                        || mHorizontalTextList.get(mHorizontalTextList.size() - 1).contains((int) (x + horizontalTextPaint.measureText(text)), (int) y)) {
                    clipRect = mHorizontalTextList.get(mHorizontalTextList.size() - 1);
                }
                LoggerUtil.d(TAG, "clipRect :" + clipRect);
                if (null != clipRect) {
                    Paint p = new Paint();
                    p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    canvas.drawRect(clipRect, p);
                }
                canvas.drawText(text, x, y, horizontalTextPaint);

            }
        }
    }

    /**
     * 是否显示横轴中间的文字
     *
     * @param show
     */
    public void setShowHorizontalAxisText(boolean show) {
        drawHorizontalAxisText = show;
    }

    /**
     * 是否绘制高亮选中文字
     */
    private boolean showHighlightText = true;

    public void setShowClickHighlightText(boolean show) {
        showHighlightText = show;
    }

    /**
     * 柱形图设置分组,和距离
     */
    public void setPartition(int partition, int distance) {
        mPartition = partition;
        mGroupDistance = distance;
        invalidate();
    }

    public int getHorizontalTextSize() {
        return horizontalTextSize;
    }

    /**
     * 横轴文字大小
     *
     * @param horizontalTextSize
     */
    public void setHorizontalTextSize(int horizontalTextSize) {
        this.horizontalTextSize = horizontalTextSize;
    }

    public int getVerticalTextSize() {
        return verticalTextSize;
    }

    /**
     * 纵轴文字大小
     *
     * @param verticalTextSize
     */
    public void setVerticalTextSize(int verticalTextSize) {
        this.verticalTextSize = verticalTextSize;
    }

    public int getCharWidth() {
        return mCharWidth;
    }

    /**
     * 设置Char的宽度
     *
     * @param mCharWidth
     */
    public void setCharWidth(int mCharWidth) {
        this.mCharWidth = mCharWidth;
    }

    public int getPaddingBarLeft() {
        return paddingBarLeft;
    }

    /**
     * 设置柱形图左偏移
     *
     * @param paddingBarLeft
     */
    public void setPaddingBarLeft(int paddingBarLeft) {
        this.paddingBarLeft = paddingBarLeft;
    }

    /**
     * 设置柱形图右偏移
     *
     * @param paddingBarRight
     */
    public void setPaddingBarRight(int paddingBarRight) {
        this.paddingBarRight = paddingBarRight;
    }

    /**
     * 分段总比例为10，即各个分段加起来为10
     */
    private final static int segmentTotal = 10;

    /**
     * 颜色分段
     *
     * @param proportion 分段比例
     * @param colors     颜色
     * @return
     */
    public boolean setMulticoloured(boolean open, int[] proportion, int[] colors) {
        boolean result = true;
        if (!open) {
            isOpenMulti = false;
            result = true;
        } else {

            int p = 0;
            for (int i : proportion) {
                p = +i;
            }
            if (p != segmentTotal) {
                result = false;
            }
            isOpenMulti = true;
            mProportion = proportion;
            mMultiColors = colors;
            invalidate();
        }
        return result;
    }

    private OnItemSelectListener mItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    public void setPositionSelect(int position) {
        int dataSize = mDataBean.getHorizontalAxisData().size();
        LoggerUtil.d(TAG, "设置选中 position" + position + ">>size " + dataSize);
        if (0 < dataSize && position < dataSize) {
            LoggerUtil.d(TAG, "高亮 ：" + position);
            drawHighlightChart(position);
            if (null != mItemSelectListener) {
                mItemSelectListener.onItemSelect(position);
            }
        }
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * 横向文字是否和数据绑定绘制
     *
     * @param bindWithHorizontalData
     */
    public void setBindWithHorizontalData(boolean bindWithHorizontalData) {
        this.bindWithHorizontalData = bindWithHorizontalData;
    }

    public void setDrawCharBGNoData(boolean draw) {
        drawCharBGNoData = draw;
    }
    //使用方式
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
}
