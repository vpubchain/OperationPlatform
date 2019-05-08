package com.jiuling.commonbusiness;

import android.os.Environment;

/**
 * 全局常量配置
 *
 */

public class AppConstants {

    public static final String PACKAGE = "com.lianyun.softwallet";
    public static final String PACKAGE_PATH = "data/data/com.lianyun.softwallet/";
    public static final String EXTERNALDIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    public static final String HOST = "https://paas.vpubchain.org/";
}
