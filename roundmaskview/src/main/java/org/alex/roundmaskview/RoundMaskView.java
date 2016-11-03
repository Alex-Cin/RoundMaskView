package org.alex.roundmaskview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import org.alex.view.BaseView;

/**
 * 作者：Alex
 * 时间：2016/11/3 11:38
 * 简述：
 *
 * @link http://www.cnblogs.com/itchq/p/3938506.html
 * @link https://github.com/sanchi3/CircularCoverView
 * @link http://www.cnblogs.com/mysearchblog/p/5926288.html
 */
public class RoundMaskView extends BaseView {
    private float topLeftRadius;
    private float bottomLeftRadius;
    private float topRightRadius;
    private float bottomRightRadius;
    private int backgroundColor;
    private float radius;

    public RoundMaskView(Context context) {
        super(context);
    }

    public RoundMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        radius = dp2px(8);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundMaskView);
        radius = typedArray.getDimension(R.styleable.RoundMaskView_rmv_radius, radius);

        topLeftRadius = radius;
        bottomLeftRadius = radius;
        topRightRadius = radius;
        bottomRightRadius = radius;

        bottomLeftRadius = typedArray.getDimension(R.styleable.RoundMaskView_rmv_bottomLeftRadius, bottomLeftRadius);
        bottomRightRadius = typedArray.getDimension(R.styleable.RoundMaskView_rmv_bottomRightRadius, bottomRightRadius);
        topLeftRadius = typedArray.getDimension(R.styleable.RoundMaskView_rmv_topLeftRadius, topLeftRadius);
        topRightRadius = typedArray.getDimension(R.styleable.RoundMaskView_rmv_topRightRadius, topRightRadius);
        backgroundColor = typedArray.getColor(R.styleable.RoundMaskView_rmv_backgroundColor, Color.parseColor("#FFFFFF"));
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setStyle(Paint.Style.FILL);
        /*
        * ALL_SAVE_FLAG	保存全部的状态
        * CLIP_SAVE_FLAG	保存裁剪的某个区域的状态
        * CLIP_TO_LAYER_SAVE_FLAG	保存预先设置的范围里的状态
        * FULL_COLOR_LAYER_SAVE_FLAG	保存彩色涂层
        * HAS_ALPHA_LAYER_SAVE_FLAG	不透明图层保存
        * MATRIX_SAVE_FLAG	Matrix信息(translate，rotate，scale，skew)的状态保存
        *
        * */
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        //draw sector-dst-bitmap at first.
        canvas.drawBitmap(drawSector(getWidth(), getHeight()), 0, 0, paint);
        //set Xfermode of paint.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        //then draw rect-src-bitmap
        canvas.drawBitmap(drawRect(getWidth(), getHeight()), 0, 0, paint);
        paint.setXfermode(null);
        //restore the canvas
        canvas.restoreToCount(sc);
    }


    /**
     * create a sector-bitmap as the dst.
     *
     * @param w width of bitmap
     * @param h height of bitmap
     * @return bitmap
     */
    private Bitmap drawSector(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        c.drawArc(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2), 180, 90, true, p);
        c.drawArc(new RectF(0, getHeight() - bottomLeftRadius * 2, bottomLeftRadius * 2, getHeight()), 90, 90, true, p);
        c.drawArc(new RectF(getWidth() - topRightRadius * 2, 0, getWidth(), topRightRadius * 2), 270, 90, true, p);
        c.drawArc(new RectF(getWidth() - bottomRightRadius * 2, getHeight() - bottomRightRadius * 2, getWidth(), getHeight()), 0, 90, true, p);
        return bm;
    }

    /**
     * create a rect-bitmap as the src.
     *
     * @param w width of bitmap
     * @param h height of bitmap
     * @return bitmap
     */
    private Bitmap drawRect(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(backgroundColor);

        c.drawRect(new RectF(0, 0, topLeftRadius, topLeftRadius), p);
        c.drawRect(new RectF(0, getHeight() - bottomLeftRadius, bottomLeftRadius, getHeight()), p);
        c.drawRect(new RectF(getWidth() - topRightRadius, 0, getWidth(), topRightRadius), p);
        c.drawRect(new RectF(getWidth() - bottomRightRadius, getHeight() - bottomRightRadius, getWidth(), getHeight()), p);
        return bm;
    }

}
