package com.jiuling.operate.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.operate.base.BaseActivity;


/**
 * Created by Administrator on 2017/11/24.
 */
public abstract class ShowPopUtil {

    private PopupWindow mPop;
    private Activity activity;
    private int layout;
    private double width=0;
    private double height=0;
    private boolean isBottom=false;

    public ShowPopUtil(int layout, Activity activity){
        this.layout=layout;
        this.activity=activity;
    }

    public ShowPopUtil(int layout, Activity activity, double width, double height, boolean isBottom){
        this.layout=layout;
        this.activity=activity;
        this.width=width;
        this.height=height;
        this.isBottom=isBottom;
    }


    public ShowPopUtil(int layout, Activity activity, double width, double height){
        this.layout=layout;
        this.activity=activity;
        this.width=width;
        this.height=height;
    }



    public void dismiss(){
        if (mPop!=null){
            mPop.dismiss();
        }
    }

    public void showPop(){
        darkenBackground(0.6f,activity);
        View view = LayoutInflater.from(activity).inflate(layout, null);

        if (mPop == null) {
            mPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            initView(view);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        } else {
            mPop.setBackgroundDrawable(new BitmapDrawable());
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(false);
            mPop.setTouchable(true);
//            mPop.setAnimationStyle(R.style.TranslationPopupWindowAnimation);
            if (width!=0){
                mPop.setWidth((int) (ProgressBaseActivity.mScreenWidth * width));
                Log.i("widthwidth",(int) (ProgressBaseActivity.mScreenWidth * width)+"");
            }
            if (height!=0){
                mPop.setHeight((int) (ProgressBaseActivity.mScreenHeight * height));
                Log.i("widthwidth",(int) (ProgressBaseActivity.mScreenHeight * height)+"高");
            }
            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    darkenBackground(1.0f,activity);
                    mPop.dismiss();
                }
            });

            if (!isBottom){
                mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
            }else{
                mPop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }

        }
    }

    protected abstract void initView(View view);



    public void darkenBackground(Float bgcolor, Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgcolor;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }


}