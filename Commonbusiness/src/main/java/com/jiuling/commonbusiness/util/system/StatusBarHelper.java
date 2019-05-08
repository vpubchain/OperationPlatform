package com.jiuling.commonbusiness.util.system;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.ContentValues.TAG;

/**
 * 适配4.4以上版本 MIUI6、Flyme 和其他 Android6.0 及以上版本状态栏字体颜色
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         2017/6/2
 */
public class StatusBarHelper {

    public static final int MIUI = 1;
    public static final int FLYME = 2;
    public static final int ANDROID_M = 3;
    public static final int OTHER = 4;

    @IntDef({MIUI, FLYME, ANDROID_M, OTHER})
    @Retention(RetentionPolicy.SOURCE)
    @interface SystemType {
    }

    public static int statusBarLightMode(Activity activity) {
        return statusMode(activity, true);
    }

    public static int statusBarDarkMode(Activity activity) {
        return statusMode(activity, false);
    }

    private static int statusMode(Activity activity, boolean isFontColorDark) {
        @SystemType int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = MIUI;
            } else if (new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = FLYME;
            } else if (new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = ANDROID_M;
            }
        }
        return result;
    }


    public static void statusBarLightMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, true);

    }

    public static void statusBarDarkMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, false);
    }

    private static void statusBarMode(Activity activity, @SystemType int type, boolean isFontColorDark) {
        if (type == MIUI) {
            new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark);
        } else if (type == FLYME) {
            new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark);
        } else if (type == ANDROID_M) {
            new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark);
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @return
     */
    public static int getSystemStatusBarHeight(Activity activity) {
        return getSystemBarHeight(activity, "status_bar_height");
    }

    /**
     * 获取系统虚拟按钮高度
     *
     * @return
     */
    public static int getSystemNavigationBarHeight(Activity activity) {
        return getSystemBarHeight(activity, "navigation_bar_height");
    }

    /**
     * 反射手机运行的类：android.R.dimen.status_bar_height
     *
     * @param name
     * @return
     */
    private static int getSystemBarHeight(Activity activity, String name) {
        int height = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();        //创建实例
            int heightDp = (int) clazz.getField(name).get(object);  //反射系统获取的高度
            height = activity.getResources().getDimensionPixelSize(heightDp);    //转化为px
            return height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    /**
     * 判断当前的Navigation是否显示了
     * 可以根据Window的高度减去我们PhoneWindows的高度去直接判断
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasNavigationBarShow(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        //获取到了Window的高度，里面包含了NavigationBar的高度，但是不包含StatusBar高度
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        int windowHeight = metrics.heightPixels;
        int windowWidth = metrics.widthPixels;

        //获取到我们的PhoneWindow的宽高信息，不包含NavigationBar高度
        metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int phoneWindowHeight = metrics.heightPixels;
        int phoneWindowWidth = metrics.widthPixels;

        //相减获取到NavigationBar的高度，这里还做了横竖屏的判断
        int height = windowHeight - phoneWindowHeight;
        int width = windowWidth - phoneWindowWidth;
        Log.i(TAG, "Window的高度：" + windowHeight + "\n宽度：" + windowWidth);
        Log.i(TAG, "PhoneWindow的高度：" + phoneWindowHeight + "\n宽度：" + phoneWindowWidth);
        Log.e(TAG, "输出当前的height：" + height + "\nwidth：" + width);
        return height > 0 || width > 0;
    }
}
