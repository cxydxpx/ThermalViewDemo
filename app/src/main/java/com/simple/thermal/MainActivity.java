package com.simple.thermal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ThermalView thermalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thermalView = findViewById(R.id.thermal_view);

        initData();

    }


    static float[][] arr = new float[32][32];

    static {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = (float) (Math.random() * 50.0);
            }
        }
    }


    /**
     * 生成max到min范围的浮点数
     */
    public static double nextDouble(final double min, final double max) {
        return min + ((max - min) * new Random().nextDouble());
    }

    static ValueBean[][] arrValue = new ValueBean[32][32];

    private String TAG = "mTag";

    private static DecimalFormat fmt = new DecimalFormat("#0.0");

    private void initData() {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                float value = arr[i][j];

                int color = parseValue(value);

                Log.i(TAG, "initData: " + value);

                arrValue[i][j] = new ValueBean(value, color);
            }
        }

        thermalView.setData(arrValue);

    }

    private int parseValue(float value) {

        if (value >= 0 && value <= 10) {
//            B = 255G = 0R = 255 -> 0
            return parseFirstStage(value);
        } else if (value > 10 && value <= 20) {
            return parseSecondStage(value);
        } else if (value > 20 && value <= 30) {
            return parseThirdStage(value);
        } else if (value > 30 && value <= 40) {
            return parseFourthStage(value);
        } else if (value > 40 && value <= 50) {
            return parseFifthStage(value);
        } else {
            // 低于0度及大于50度的返回白色
            return Color.argb(255, 255, 255, 255);
        }

    }

    /**
     * 第五阶段
     *
     * @param value
     * @return
     */
    private int parseFifthStage(float value) {
        //      R = 255 B =0 G = 255 -> 0 40-50

        float v = (float) (255.0 % 100.0);

        /**
         *  40-50 一百等份儿 倒叙
         *  value 当前温度
         *  v 是每一等份儿的值
         */
        double colorValue = 255 - 10 * v * (value - 40);

        int color = Color.argb(255, 255, (int) colorValue, 0);

        return color;
    }

    /**
     * 第四阶段
     *
     * @param value
     * @return
     */
    private int parseFourthStage(float value) {
        //      G = 255 B= 0 R = 0 -> 255 30 - 40

        float v = (float) (255.0 % 100.0);

        /**
         *  30 - 40 一百等份儿 倒叙
         *  value 当前温度
         *  v 是每一等份儿的值
         */
        double colorValue = 10 * v * (value - 30);

        int color = Color.argb(255, (int) colorValue, 255, 0);

        return color;
    }

    /**
     * 第三阶段
     *
     * @param value
     * @return
     */
    private int parseThirdStage(float value) {
        //       G = 255 R = 0 B = 255 -> 0 20 - 30

        float v = (float) (255.0 % 100.0);

        /**
         *  20 - 30 一百等份儿 倒叙
         *  value 当前温度
         *  v 是每一等份儿的值
         */
        double colorValue = 255 - 10 * v * (value - 20);

        int color = Color.argb(255, 0, 255, (int) colorValue);

        return color;
    }

    /**
     * 第二阶段
     *
     * @param value
     * @return
     */
    private int parseSecondStage(float value) {
        //        B = 255  R = 0  G = 0 -> 255 10 - 20

        float v = (float) (255.0 % 100.0);

        /**
         *  10 - 20 一百等份儿 倒叙
         *  value 当前温度
         *  v 是每一等份儿的值
         */
        double colorValue = 10 * v * (value - 10);

        int color = Color.argb(255, 0, (int) colorValue, 255);

        return color;
    }

    /**
     * 第一阶段
     *
     * @param value
     * @return
     */
    private int parseFirstStage(float value) {
//        B = 255  G = 0  R = 255 -> 0  100

        float v = (float) (255.0 % 100.0);

        /**
         *  1 - 10 一百等份儿 倒叙
         *  value 当前温度
         *  v 是每一等份儿的值
         */
        double colorValue = 255 - 10 * v * value;

        int color = Color.argb(255, (int) colorValue, 0, 255);

        return color;

    }

}
