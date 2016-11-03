package org.alex.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 作者：Alex
 * 时间：2016年10月02日
 * 简述：
 */
@SuppressWarnings("ALL")
public class RatioRelativeLayout extends RelativeLayout implements IRatioView {
    /**
     * 高   ÷  宽
     */
    private float hRationW;

    public RatioRelativeLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.scaleY});
        hRationW = typedArray.getFloat(0, 1F);
		typedArray.recycle();
    }

    /**
     * 高 除以 宽
     */
    @Override
    public void setHRationW(float hRationW) {
        this.hRationW = hRationW;
        invalidate();
    }

    @Override
    public void setScaleY(float scaleY) {
        super.setScaleY(1F);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = (int) (MeasureSpec.getSize(widthMeasureSpec) * hRationW);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
