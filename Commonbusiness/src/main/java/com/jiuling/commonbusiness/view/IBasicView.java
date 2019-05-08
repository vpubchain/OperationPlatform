package com.jiuling.commonbusiness.view;

import java.util.ArrayList;

/**
 * View层接口
 * Created by hasee on 2017/1/10.
 */
public interface IBasicView<T> {

    /**
     * 用于显示数据加载框
     */
    void showLoading();

    /**
     * 加载框dismiss
     */
    void dismissLoading();

    /**
     * View展示数据方法
     * @param data
     */
    void showData(ArrayList<T> data);

    /**
     * 加载失败显示信息
     * @param errorMsg
     */
    void showErrorMsg(String errorMsg);

    void requestTimeOut();

}
