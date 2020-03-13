package com.simple.thermal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

public class ThermalView extends View {


    public ThermalView(Context context) {
        super(context);
        initView(context);
    }

    public ThermalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ThermalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private Paint mPaint;
    private Paint mPaintT;

    static ValueBean[][] arr = new ValueBean[32][32];


    private void initView(Context context) {

        int displayWidth = getDisplayWidth(context);
        mean = (float) ((displayWidth - 100) / 36);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) mean);

        mPaintT = new Paint();
        mPaintT.setColor(Color.BLACK);
        mPaintT.setTextSize(12);
        mPaintT.setStrokeCap(Paint.Cap.BUTT);
        mPaintT.setAntiAlias(true);
        mPaintT.setStrokeWidth(mean);

        Log.i(TAG, "initView: " + mean);

    }

    private float mean;

    /**
     * 获取屏幕宽度
     *
     * @param context
     */
    private int getDisplayWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point sizePoint = new Point();
        display.getSize(sizePoint);
        int width = sizePoint.x;
        int height = sizePoint.y;

        return width;
    }


    private String TAG = "mTag";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            ValueBean[] j = arr[i];
            for (int k = 1; k < j.length; k++) {
                ValueBean bean = j[k];
//                Log.i(TAG, "onDraw: " + i + " == " + k);
                mPaint.setColor(bean.getColor());
                canvas.drawPoint(50 + (k * mean), 50 + i * mean, mPaint);
                canvas.drawText(bean.getValue() + "", 50 + k * mean, 50 + i * mean, mPaintT);

            }
        }
    }

    public void setData(ValueBean[][] arrValue) {
        for (int i = 0; i < arrValue.length; i++) {
            for (int j = 0; j < arrValue[i].length; j++) {
                arr[i][j] = arrValue[i][j];
            }
        }
        invalidate();
    }
}
