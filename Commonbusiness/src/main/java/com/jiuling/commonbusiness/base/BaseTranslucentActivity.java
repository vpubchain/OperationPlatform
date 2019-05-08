package com.jiuling.commonbusiness.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.commonbusiness.util.system.StatusBarHelper;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;


import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * desc:
 * 支持5.0版本以上的透明状态栏设置
 *
 * @author Rayman
 * @date 2017/10/30
 */

public abstract class BaseTranslucentActivity<T extends IBasicContract.IPresenterImpl> extends AppCompatActivity implements IBasicContract.IViewImpl {

    protected IBasicContract.IPresenterImpl mPresenter;
    private WeakReference<View> mToolBar;
    private WeakReference<View> mNavigation;

    //像素
    protected int mScreenWidth;
    protected int mScreenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setBackgroundDrawable(null);

        initTranslucentCompat();
        Logger.i("BaseActivity执行次数");
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        //获取当前屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;


        setContentView(setLayoutID());
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    protected abstract int setLayoutID();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    /**
     * 根据API判断当前沉浸式设计的实现效果
     */
    private void initTranslucentCompat() {
        //基于[4.4~5.0)版本的沉浸式菜单栏设计
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            //4.4以下的版本不做兼容
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setTranslucentView(View toolBar, View navView) {
        mToolBar = new WeakReference<View>(toolBar);
        mNavigation = new WeakReference<View>(navView);
        if (mToolBar.get() != null) {
            View view = mToolBar.get();
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height += StatusBarHelper.getSystemStatusBarHeight(this);
            view.setLayoutParams(params);
            view.setPadding(
                    view.getPaddingLeft(),
                    view.getPaddingTop() + StatusBarHelper.getSystemStatusBarHeight(this),
                    view.getPaddingRight(),
                    view.getPaddingBottom()
            );
        }

        if (mNavigation.get() != null) {
            if (StatusBarHelper.hasNavigationBarShow(this)) {
                //当前界面显示了NavigationBar
                View view = mNavigation.get();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height += StatusBarHelper.getSystemNavigationBarHeight(this);
                view.setLayoutParams(params);
            } else {
                Log.e(TAG, "当前手机没有设置NavigationBar");
            }
        }
    }

    /**
     * 设置沉浸式设计的颜色
     *
     * @param color
     */
    public void setTranslucentColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mToolBar != null && mToolBar.get() != null) {
                mToolBar.get().setBackgroundColor(getResources().getColor(color));
            }
            if (mNavigation != null && mNavigation.get() != null) {
                mNavigation.get().setBackgroundColor(getResources().getColor(color));
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //5.0以上的版本
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract T createPresenter();

    public void darkenBackground(Float bgcolor) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgcolor;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }

    /**
     * IsDarkMode
     *
     * @param isDark
     */
    public void setTranslucentMode(boolean isDark) {
        if (isDark) {
            StatusBarHelper.statusBarDarkMode(this);
        } else {
            StatusBarHelper.statusBarLightMode(this);
        }
    }

}
