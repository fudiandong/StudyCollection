package com.fudd.thisview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.fudd.thisview.MeasureUtil;

/**
 * Created by fudd-office on 2017-3-23 16:17.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class PorterDuffView extends View {
    private PorterDuff.Mode mode = PorterDuff.Mode.DST;
    private Paint paint;
    private int screenW;//屏幕宽度
    private int screenH; // 屏幕高度
    private PorterDuffXfermode porterDuffXfermode;// 图形混合模式
    private static final int RECT_SIZE_SMALL = 400; // 两边小正方形的尺寸
    private static final int RECT_SIZE_BIG = 800; // 两边小正方形的尺寸
    private int rect_left_x = 0;
    private int rect_left_y = 0;
    private int rect_right_x,rect_right_y;
    private int rect_big_x,rect_big_y;

    private Point mPoint;

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 实例化并设置抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
        mPoint = new Point();
        porterDuffXfermode = new PorterDuffXfermode(mode);
//        计算坐标
        calu(context);
    }

    private void calu(Context context) {
        int[] size = MeasureUtil.getScreenSize((Activity) context);
        screenW = size[0];
        screenH = size[1];
        // 右边正方形原点
        rect_right_x = screenW - RECT_SIZE_SMALL;
        rect_right_y = 0;
        // 中间大正方形原点
        rect_big_x = screenW/2 - RECT_SIZE_BIG/2;
        rect_big_y = (screenH-RECT_SIZE_SMALL)/2 - RECT_SIZE_BIG/2 + RECT_SIZE_SMALL;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        mPoint.set(RECT_SIZE_SMALL, RECT_SIZE_SMALL);
//        canvas.drawRect(0,0,400,400,paint);
        canvas.drawBitmap(initSrcBitmap(400),0,0,paint);// 画源图--左
        canvas.drawBitmap(initDisBitmap(400),rect_right_x,rect_right_y,paint); //  画目标图 -- 右

        int sc = canvas.saveLayer(0,0,screenW,screenH,null,Canvas.ALL_SAVE_FLAG);

        // 重新设置尺寸值 中间正方形
        mPoint.set(RECT_SIZE_BIG,RECT_SIZE_BIG);

        canvas.drawBitmap(initDisBitmap(800),rect_big_x,rect_big_y,paint);

        paint.setXfermode(porterDuffXfermode);

        canvas.drawBitmap(initSrcBitmap(800),rect_big_x,rect_big_y,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);


    }

    public Bitmap initSrcBitmap(int size) {
        int[] pixels = new int[mPoint.x * mPoint.y];
        int dst = 0;
        for (int row = 0; row < mPoint.y; ++row) {
            for (int col = 0; col < mPoint.x; ++col) {
                pixels[dst++] = color((float) (mPoint.y - row) / mPoint.y, (float) (mPoint.x - col) / mPoint.x, (float) (mPoint.x - col) / mPoint.x, (float) col / mPoint.x);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    public Bitmap initDisBitmap(int size) {
        int[] pixels = new int[mPoint.x * mPoint.y];
        int dst = 0;
        for (int row = 0; row < mPoint.y; ++row) {
            for (int col = 0; col < mPoint.x; ++col) {
                pixels[dst++] = color((float) (mPoint.x - col) / mPoint.x, (float) (mPoint.y - row) / mPoint.x, (float) row / mPoint.y, (float) row / mPoint.y);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888);
        return bitmap;
    }
    private int color(float Alpha, float R, float G, float B) {
        return (int) (Alpha * 255) << 24 | (int) (R * Alpha * 255) << 16 | (int) (G * Alpha * 255) << 8 | (int) (B * Alpha * 255);
    }
}
