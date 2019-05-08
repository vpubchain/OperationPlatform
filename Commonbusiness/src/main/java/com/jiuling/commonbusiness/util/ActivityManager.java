package com.jiuling.commonbusiness.util;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：yeayea on 2015-10-2015/10/17 0017 16:34
 * 描述：
 */
public class ActivityManager extends Application {

    private static Map<String, Activity> mdestoryMap = new HashMap<>();
    private WeakReference<Activity> sCurrentActivityWeakRef;

    public ActivityManager() {
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 传入需要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        mdestoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定的activity
     *
     * @param activityName 传入需要销毁的activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = mdestoryMap.keySet();
        for (String key : keySet) {
            if (key.equals(activityName)) {
                mdestoryMap.get(key).finish();
            }

        }
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    /**
     * 结束所有活动
     */
    public static void finishAll() {
        Set<String> keySet = mdestoryMap.keySet();
        for (String key : keySet) {
            mdestoryMap.get(key).finish();
        }
        mdestoryMap.clear();
    }

    public static List<String> getActivities() {
        List<String> activities = new ArrayList<>();
        for (Map.Entry<String, Activity> activity : mdestoryMap.entrySet()) {
            activities.add(activity.getKey());
        }
        return activities;
    }
}
