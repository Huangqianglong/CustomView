package com.hql.customview.heartbeat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hql.customview.OnItemSelectListener;
import com.hql.uitls.LoggerUtil;
import com.hql.uitls.ViewUtils;

import java.util.ArrayList;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/15
 * <br /> Description :曲线图表
 */
public class CurveView extends View {
    private static final String TAG = "CurveView";
    /**
     * 宽
     */
    private int mViewWidth;
    /**
     * 高
     */
    private int mViewHeight;
    /**
     * X轴主线的起点坐标
     */
    float mAxisStartY;
    /**
     * X轴主线的结束坐标
     */
    float mAxisEndX;
    /**
     * 横轴文字画笔
     */
    private Paint horizontalTextPaint;
    /**
     * 纵轴文字画笔
     */
    private Paint verticalTextPaint;
    /**
     * 高亮圆点笔
     */
    private Paint highlightPaint;

    /**
     * 圆环颜色
     */
    private int circleRingColor = Color.parseColor("#FFFFFF");
    /**
     * 圆心颜色
     */
    private int circlePointColor = Color.parseColor("#3B97A3");
    /**
     * 画阴影
     */
    private Paint curvePaint;

    /**
     * 曲线颜色
     */
    private int curveColor = Color.parseColor("#D64E6F");
    /**
     * 画阴影
     */
    private Paint curvePaint2;
    private Paint tailorPaint;
    /**
     * 横轴背景线颜色
     */
    private int horizontalLineColor = 0xff353A4B;

    private CurveData mData;
    /**
     * 横轴背景线画笔
     */
    private Paint horizontalLinePaint;
    /**
     * 是否绘制高亮状态
     */
    boolean isDrawHighlight = false;
    /**
     * 选中高亮的index
     */
    private int mHighlightIndex;
    private LinearGradient mShader;
    /**
     * 纵轴虚线画笔
     */
    private Paint verticalDottedPaint;
    /**
     * 虚线颜色
     */
    private int dottedColor = 0xff2FCAE8;
    private int verticalTextPadding = 26;

    public CurveView(Context context) {
        super(context);
        init();
    }

    public CurveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //横轴线
        horizontalLinePaint = new Paint();
        horizontalLinePaint.setColor(horizontalLineColor);
        horizontalLinePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
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

        //曲线笔
        curvePaint = new Paint();
        curvePaint.setColor(curveColor);
        curvePaint.setAntiAlias(true);
        //curvePaint.setStyle(Paint.Style.FILL);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setTextSize(28f);
        curvePaint.setStrokeWidth(10f);

        curvePaint2 = new Paint();
        curvePaint2.setColor(curveColor);
        curvePaint2.setAntiAlias(true);
        //curvePaint.setStyle(Paint.Style.FILL);
        curvePaint2.setStyle(Paint.Style.FILL);
        curvePaint2.setTextSize(28f);
        curvePaint2.setStrokeWidth(5f);
        curvePaint2.setShader(mShader);
        curvePaint2.setShadowLayer(150, 10, 10, Color.parseColor("#FF506D"));


        tailorPaint = new Paint();
        tailorPaint.setStyle(Paint.Style.FILL);
        tailorPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        tailorPaint.setShader(null);

        highlightPaint = new Paint();
        highlightPaint.setAntiAlias(true);
        highlightPaint.setStyle(Paint.Style.FILL);

        mShader = new LinearGradient(mAxisEndX / 2,
                //按比例计算高度
                0,
                mAxisEndX / 2,
                mAxisStartY,
                new int[]{shadowColor1, shadowColor2}, new float[]{0.6f, 0.9f},
                //Color.RED, 0xff000000,
                Shader.TileMode.MIRROR);

        //虚线
        verticalDottedPaint = new Paint();
        verticalDottedPaint.setColor(dottedColor);
        verticalDottedPaint.setAntiAlias(true);
        verticalDottedPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateAxis(canvas);
        if (isDrawHighlight) {
            drawHighlightChart(canvas);
            isDrawHighlight = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void drawCoordinateAxis(Canvas canvas) {
        if (mData == null || (mData.getHorizontalAxisTex().size() < 1 || mData.getVerticalAxisTex().size() < 1)) {
            Log.e(TAG, "数据空");
            return;
        }
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
            float textWidth = 60 + horizontalTextPaint.measureText(mData.getHorizontalAxisTex().get(0)) + 5;
            mAxisEndX = endX = mViewWidth - textWidth;
        } else {
            endX = mAxisEndX;
        }

        //间距
        float interval;


        ArrayList<HeartBeatBean> points = mData.getHeartBeatData();
        interval = mAxisStartY / mData.getVerticalAxisTex().size();


//        //画纵向的几个横线 和纵轴的文字
        if (mData.getVerticalAxisTex().size() == 0) {
            canvas.drawLine(0, height, endX, height, horizontalLinePaint);
        } else {
            ArrayList<String> textList = mData.getVerticalAxisTex();
            int i = 0;
            horizontalLinePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            while (height > 0) {
                canvas.drawLine(0, height, endX, height, horizontalLinePaint);
                if (i < textList.size()) {
                    canvas.drawText(textList.get(i), endX + verticalTextPadding, height, verticalTextPaint);
                }
                i++;
                height = height - interval;
                //Log.d(TAG, "1划线间距  :" + interval + ">>y:" + height);
            }
        }
        // 曲线
        Path curPath = new Path();
        Path path2 = new Path();
        interval = mAxisEndX / (points.size() - 1);
        for (int i = 0; i < points.size(); i++) {
            if (i > mClickRectList.size() - 1) {
                mClickRectList.add(new Rect((int) (interval * i - interval / 2),
                        0,
                        (int) (interval * i + interval / 2),
                        (int) mAxisStartY
                ));
            }
            float y = mAxisStartY - mData.getHeartBeatData().get(i).getRate() / mData.getMaxData() * mAxisStartY;

            Point startPoint = new Point((int) (interval * i), (int) y);
            Point nextPoint = new Point();
            if (i != points.size() - 1) {
                float yNext;
                yNext = mAxisStartY - mData.getHeartBeatData().get(i + 1).getRate() / mData.getMaxData() * mAxisStartY;

                nextPoint = new Point((int) (interval * (i + 1)), (int) yNext);

                int wt = (startPoint.x + nextPoint.x) / 2;

                Point p3 = new Point();
                Point p4 = new Point();
                p3.y = startPoint.y;
                p3.x = wt;
                p4.y = nextPoint.y;
                p4.x = wt;

                if (i == 0) {
                    curPath.moveTo(startPoint.x, startPoint.y);
                    path2.moveTo(startPoint.x, startPoint.y);
                    //canvas.drawCircle(startPoint.x, startPoint.y,3,horizontalTextPaint);
                }
                /**
                 *添加一个立方贝塞尔曲线的最后一点,接近控制点
                 *(x1,y1)和(x2,y2),到最后(x3,y3)。如果没有移至()调用
                 *为这个轮廓,第一点是自动设置为(0,0)。
                 *
                 * @param x1的坐标1立方曲线控制点
                 * @param y1第一控制点的坐标一立方曲线
                 * @param x2上第二个控制点的坐标一立方曲线
                 * @param y2第二控制点的坐标一立方曲线
                 * @param x3的坐标三次曲线的终点
                 * @param y3终点坐标的三次曲线
                 *
                 */
                curPath.cubicTo(p3.x, p3.y, p4.x, p4.y, nextPoint.x, nextPoint.y);
                path2.cubicTo(p3.x, p3.y, p4.x, p4.y, nextPoint.x, nextPoint.y);
                //Log.d(TAG, "(" + interval * i + "," + y + ")" + ">>>end" + mAxisEndX + ">>interval:" + interval);
            } else {
                //   if (i == points.size() ){
//                y = mData.getHeartBeatData().get(mData.getHeartBeatData().size() - 1) / mData.getMaxData() * mAxisStartY;
//                canvas.drawCircle(mAxisEndX, y, 3, horizontalLinePaint);

//                /**连接到终点x,底部y*/
//                curPath.lineTo(mAxisEndX, mAxisStartY);
//                /**连接到起点x,底部y*/
//                curPath.lineTo(0, mAxisStartY);
//                /**连接到起点x,起点y*/
//                curPath.lineTo(0, mData.getHeartBeatData().get(0) / mData.getMaxData() * mAxisStartY);


                /**连接到终点x,底部y*/
                path2.lineTo(mAxisEndX, 0);
                /**连接到起点x,底部y*/
                path2.lineTo(0, 0);
                /**连接到起点x,起点y*/
                path2.lineTo(0, mAxisStartY - (mAxisStartY - mData.getHeartBeatData().get(0).getRate() / mData.getMaxData() * mAxisStartY));
            }
            //Log.d(TAG, "(" + interval * i + "," + y + ")" + ">>>end" + mAxisEndX + ">>interval:" + interval);
        }


        //画下半部
        canvas.drawPath(curPath, curvePaint);
        //画上半部分
        canvas.drawPath(path2, curvePaint2);
        //裁剪上半部分，取阴影,并剪切阴影多余的部分
        canvas.drawPath(path2, tailorPaint);
        canvas.drawRect(mAxisEndX, 0, mViewWidth, mViewHeight, tailorPaint);
        canvas.drawRect(0, mAxisStartY, mViewWidth, mViewHeight, tailorPaint);


        //画纵向的几个横线 和纵轴的文字
        height = mAxisStartY;
        interval = mAxisStartY / mData.getVerticalAxisTex().size();

        if (mData.getVerticalAxisTex().size() == 0) {
            canvas.drawLine(0, height, endX, height, horizontalLinePaint);
        } else {
            ArrayList<String> textList = mData.getVerticalAxisTex();
            int i = 0;
            horizontalLinePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
            while (height > 0) {
                canvas.drawLine(0, height, endX, height, horizontalLinePaint);
                if (i < textList.size()) {
                    canvas.drawText(textList.get(i), endX + verticalTextPadding, height, verticalTextPaint);
                }
                i++;
                height = height - interval;
                //Log.d(TAG, "2划线间距  :" + interval + ">>y:" + height);
            }
        }

        //画横轴文字
        ArrayList<String> horizonText = mData.getHorizontalAxisTex();
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

    private ArrayList<Rect> mClickRectList = new ArrayList<>();

    public void setData(CurveData data) {
        this.mData = data;
        mClickRectList.clear();
    }

    boolean isFullModel = false;
    private int maxIndex;

    /**
     * 横轴是否全部填充
     */
    public void isFullModel(boolean full, int max) {
        isFullModel = full;
        maxIndex = max;
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
        ArrayList<HeartBeatBean> points = mData.getHeartBeatData();
        float interval = mAxisEndX / (points.size() - 1);
        float y = mAxisStartY - mData.getHeartBeatData().get(mHighlightIndex).getRate() / mData.getMaxData() * mAxisStartY;
        highlightPaint.setColor(circleRingColor);
        canvas.drawCircle(interval * mHighlightIndex, y, 10, highlightPaint);
        highlightPaint.setColor(circlePointColor);
        canvas.drawCircle(interval * mHighlightIndex, y, 6, highlightPaint);
        canvas.drawLine(interval * mHighlightIndex, y, interval * mHighlightIndex, mAxisStartY, verticalDottedPaint);

    }

    private OnItemSelectListener mItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    private int shadowColor1 = Color.parseColor("#FF4E7D");
    private int shadowColor2 = Color.parseColor("#FF8989");

    /**
     * 阴影色
     */
    public void setShadowColor(int color1, int color2) {
        shadowColor1 = color1;
        shadowColor2 = color2;
    }

    /**
     * 设置横轴背景线颜色
     *
     * @param horizontalLineColor
     */
    public void setHorizontalLineColor(int horizontalLineColor) {
        this.horizontalLineColor = horizontalLineColor;
    }

    public CurveData getData() {
        return mData;
    }

    public void setPositionSelect(int position) {
        int dataSize = mData.getHeartBeatData().size();
        LoggerUtil.d(TAG, "setPositionSelect ：" + position+">>"+dataSize);
        if (0 < dataSize && position < dataSize) {
            LoggerUtil.d(TAG, "高亮 ：" + position);
            drawHighlightChart(position);
            if (null != mItemSelectListener) {
                mItemSelectListener.onItemSelect(position);
            }
        }
    }

}
