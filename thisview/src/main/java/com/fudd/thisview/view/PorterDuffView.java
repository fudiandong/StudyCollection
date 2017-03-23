package com.fudd.thisview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private Paint paint;
    private int screenW;//屏幕宽度
    private int screenH; // 屏幕高度
    private static final int RECT_SIZE_SMALL = 400; // 两边小正方形的尺寸
    private static final int RECT_SIZE_BIG = 800; // 两边小正方形的尺寸
    private int rect_left_x = 0;
    private int rect_left_y = 0;
    private int rect_right_x,rect_right_y;
    private int rect_big_x,rect_big_y;
    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 实例化并设置抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
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

        canvas.drawRect(0,0,400,400,paint);


    }
}
