package com.fudd.thisview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fudd-office on 2017-3-23 09:21.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class MyView extends View implements Runnable{
    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private PorterDuffXfermode pdff;

    private int radiu;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
//        pdff = new PorterDuffXfermode(PorterDuff.Mode.ADD);
//        paint.setXfermode(pdff);

    }

    private void initPaint() {
//        1
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 抗锯齿 ---  这样更圆滑
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.LTGRAY);
//        paint.setStrokeWidth(10);

//        2
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(10);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.WHITE);

        paint3 = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
        paint3.setTextSize(80);
        paint3.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(400,400,180,paint);
        canvas.drawCircle(400,400,130,paint);

        canvas.drawRect(200,300,600,500,paint1);
        canvas.drawRect(201,301,599,499,paint2);
        canvas.drawText("已  完  成",250,431,paint3);
    }

    @Override
    public void run() {
        while (true){

            if (radiu <= 200){
                radiu +=10;
                postInvalidate();
            }else {
                radiu =0;
            }
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
