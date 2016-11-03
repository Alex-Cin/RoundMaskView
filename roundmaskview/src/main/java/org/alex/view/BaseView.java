package org.alex.view;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 作者：Alex
 * 时间：2016/10/28 12:57
 * 简述：
 */
public class BaseView extends View {
    /**
     * 本控件的 最终 宽度
     */
    protected int width;
    /**
     * 本控件的 最终 高度
     */
    protected int height;

    /**
     * 圆盘的 最终 半径
     */
    protected float radius;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 获取　Paint 对象， 默认 是抗锯齿的
     *
     * @param color 颜色
     * @param style Paint.Style
     */
    protected Paint getPaint(int color, Paint.Style style) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(style);
        return paint;
    }

    /**
     * 数据转换: dp---->px
     */
    protected int dp2px(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * sp转px
     */
    protected int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }

}
