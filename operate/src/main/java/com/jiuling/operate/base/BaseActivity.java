package com.jiuling.operate.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BaseActivity extends Activity {

    //像素
    public static int mScreenWidth;
    public static int mScreenHeight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取当前屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

}
