package com.hql.customview;

import android.graphics.Paint;
import android.view.View;

/**
 * @author zhongwh
 */
public class ViewUtils {

    public static int measureDimension(int defaultSize, int measureSpec) {
        int result;

        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize;
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 获取行高
     * @param paint 画笔
     * @return 行高
     */
    public static float getLineHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom-fm.top;
    }

    /**
     * 获取顶部到基线的距离
     * @param paint 画笔
     * @return 顶部到基线的距离
     */
    public static float getBaselineHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return 0-fm.top;
    }

    /**
     * 获取垂直居中时基线到父布局顶部的高度
     * @param height 父布局的高度
     * @param paint 画笔
     * @return
     */
    public static float getBaselineCenterV(float height,Paint paint){
        final Paint.FontMetrics fm = paint.getFontMetrics();
        final float lineH = fm.bottom-fm.top;
        return (height - lineH) / 2 - fm.top;
    }
    public static float getStringWith(){
        return 0;
    }
}
