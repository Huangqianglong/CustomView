package com.hql.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/12/21
 * <br /> Description :
 */
public class MyLinearlayout extends LinearLayout {
    private int mLastX = 0;
    private int mLastY = 0;

    public MyLinearlayout(Context context) {
        super(context);
    }

    public MyLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean intercepted = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                // 如果水平方向位移大则拦截
                if (Math.abs(deltaX) - Math.abs(deltaY) > 15) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return intercepted;
    }

}
