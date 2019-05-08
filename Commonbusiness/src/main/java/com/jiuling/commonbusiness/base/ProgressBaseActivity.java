package com.jiuling.commonbusiness.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.jiuling.commonbusiness.R;
import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.commonbusiness.util.system.StatusBarHelper;
import com.jiuling.commonbusiness.view.widget.LoadingDialog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class ProgressBaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBasicContract.IViewImpl {

    //状态view
    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    protected FrameLayout mRootView;
    //错误提示
    private TextView mTextError;
    //内容页真正的view
    protected View realContentView;

    private int statusBarHeight;

    @Inject
    public T mPresenter;

    private WeakReference<View> mToolBar;
    private WeakReference<View> mNavigation;

    //像素
    public static int mScreenWidth;
    public static int mScreenHeight;

    private BaseApplication mApplication;

    Unbinder unbinder;
    private LoadingDialog loadingDialog;

    @Override
    public void showLoading() {
        if (loadingDialog==null){
            loadingDialog=new LoadingDialog();
        }
        loadingDialog.showDialog(this);

    }

    @Override
    public void dismissLoading() {
        if (loadingDialog!=null){
            loadingDialog.dialogDismiss();
        }
    }

//    @Override
//    public void showLoading() {
////        showProgressView();
//    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showEmptyView(errorMsg);
    }

//    @Override
//    public void dismissLoading() {
//        showContentView();
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().setBackgroundDrawable(null);

        //获取当前屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

        this.mApplication = (BaseApplication) getApplication();
        setupAcitivtyComponent(mApplication.getApplicationComponent());

        setContentView(R.layout.fragment_progress);

        getSupportActionBar().hide();

        mRootView = findViewById(R.id.rootView);
        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewContent = mRootView.findViewById(R.id.view_content);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);

        mTextError = mRootView.findViewById(R.id.text_tip);


        //特殊处理


        ImageView imageView = mRootView.findViewById(R.id.iv_more);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });



        //特殊处理

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        setRealContentView();




        setStatusBarColor();
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();


    }

    protected abstract void showWindow(View view);

    private void onEmptyViewClick() {
        ToastUtils.showToast("点击");
    }

    private void setRealContentView() {

        realContentView=  LayoutInflater.from(this).inflate(setLayoutID(),mViewContent,true);
//        mUnbinder=  ButterKnife.bind(this, realContentView);

        unbinder = ButterKnife.bind(this);

    }

    //设置状态栏颜色
    private void setStatusBarColor() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.status_bar));
        }else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systemContent = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.status_bar));
            systemContent.getChildAt(0).setFitsSystemWindows(true);
            systemContent.addView(statusBarView, 0, lp);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
//        EventBus.getDefault().register(this);
    }

    protected abstract void setupAcitivtyComponent(ApplicationComponent applicationComponent);

    protected abstract int setLayoutID();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

//    /**
//     * 根据API判断当前沉浸式设计的实现效果
//     */
//    private void initTranslucentCompat() {
//        //基于[4.4~5.0)版本的沉浸式菜单栏设计
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            //5.0以上的版本
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        } else {
//            //4.4以下的版本不做兼容
//        }
//    }
//
//
//
//    /**
//     * 设置沉浸式设计的颜色
//     *
//     * @param color
//     */
//    public void setTranslucentColor(int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            if (mToolBar != null && mToolBar.get() != null) {
//                mToolBar.get().setBackgroundColor(getResources().getColor(color));
//            }
//            if (mNavigation != null && mNavigation.get() != null) {
//                mNavigation.get().setBackgroundColor(getResources().getColor(color));
//            }
//        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            //5.0以上的版本
//            getWindow().setStatusBarColor(color);
//            getWindow().setNavigationBarColor(color);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        EventBus.getDefault().unregister(this);
    }


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

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }


    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.finish);
    }


//    public void  showProgressView(){
//        showView(R.id.view_progress);
//
//    }

    public void showContentView(){

        showView(R.id.view_content);
    }

    public void showEmptyView(){


        showView(R.id.view_empty);
    }



    public void showEmptyView(String msg){


        showEmptyView();
        mTextError.setText(msg);
    }



    public void showView(int viewId){

        for(int i=0;i<mRootView.getChildCount();i++){

            if( mRootView.getChildAt(i).getId() == viewId){

                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }
            else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void requestTimeOut() {

    }
}
