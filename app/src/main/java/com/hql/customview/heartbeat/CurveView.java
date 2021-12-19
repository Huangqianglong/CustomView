package com.hql.customview.heartbeat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hql.customview.ViewUtils;

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
     * 横轴虚线画笔
     */
    private Paint curvePaint;

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
    private int horizontalLineColor = Color.WHITE;//0xff353A4B;
    private CurveData mData;
    /**
     * 横轴背景线画笔
     */
    private Paint horizontalLinePaint;

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
        curvePaint.setColor(Color.GREEN);
        curvePaint.setAntiAlias(true);
        curvePaint.setStyle(Paint.Style.FILL);
        //curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setTextSize(28f);
        curvePaint.setStrokeWidth(3f);
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

        //画纵向的几个横线 和纵轴的文字
        float interval = mAxisStartY / mData.getVerticalAxisTex().size();
        if (mData.getVerticalAxisTex().size() == 0) {
            canvas.drawLine(0, height, endX, height, horizontalLinePaint);
        } else {
            ArrayList<String> textList = mData.getVerticalAxisTex();
            int i = 0;
            while (height > 0) {
                canvas.drawLine(0, height, endX, height, horizontalLinePaint);
                if (i < textList.size()) {
                    canvas.drawText(textList.get(i), endX + 5, height, horizontalLinePaint);
                }
                i++;
                height = height - interval;

            }
        }


        Path curPath = new Path();

        ArrayList<Integer> points = mData.getHeartBeatData();
        interval = mAxisEndX / (points.size() - 1);
        for (int i = 0; i < points.size(); i++) {
            float y = mData.getHeartBeatData().get(i) / mData.getMaxData() * mAxisStartY;

            Point startPoint = new Point((int) (interval * i), (int) y);
            Point nextPoint = new Point();
            if (i != points.size() - 1) {
                float yNext;
                yNext = mData.getHeartBeatData().get(i + 1) / mData.getMaxData() * mAxisStartY;
//            if (i < points.size() -1){
//                yNext = mData.getHeartBeatData().get(i + 1) / mData.getMaxData() * mAxisStartY;
//            } else{
//                yNext = mData.getHeartBeatData().get(i) / mData.getMaxData() * mAxisStartY;
//            }

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
                Log.d(TAG, "(" + interval * i + "," + y + ")" + ">>>end" + mAxisEndX + ">>interval:" + interval);
            } else {
                //   if (i == points.size() ){
                y = mData.getHeartBeatData().get(mData.getHeartBeatData().size() - 1) / mData.getMaxData() * mAxisStartY;
                canvas.drawCircle(mAxisEndX, y, 3, horizontalLinePaint);
                //curPath.lineTo(mAxisEndX, y);
                /**连接到终点x,底部y*/
                curPath.lineTo(mAxisEndX, mAxisStartY);
                /**连接到起点x,底部y*/
                curPath.lineTo(0, mAxisStartY);
                /**连接到起点x,起点y*/
                curPath.lineTo(0, mData.getHeartBeatData().get(0) / mData.getMaxData() * mAxisStartY);
            }


            Log.d(TAG, "(" + interval * i + "," + y + ")" + ">>>end" + mAxisEndX + ">>interval:" + interval);
        }


        LinearGradient mShader = new LinearGradient(mAxisEndX / 2,
                //按比例计算高度
                0,
                mAxisEndX / 2,
                mAxisStartY,
                new int[]{Color.parseColor("#BAEFE6"), Color.parseColor("#00000000")}, new float[]{0.6f, 0.9f},
                //Color.RED, 0xff000000,
                Shader.TileMode.MIRROR);
        curvePaint.setShader(mShader);
        canvas.drawPath(curPath, curvePaint);
    }

    public void setData(CurveData data) {
        this.mData = data;
    }
}
