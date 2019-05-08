package com.jiuling.commonbusiness.presenter;

/**
 * Presenter Model与View之间的通信桥梁
 * Created by hasee on 2017/1/10.
 */
public interface IBasicPresenter<T> {

    void fetch();   //获取数据
}
