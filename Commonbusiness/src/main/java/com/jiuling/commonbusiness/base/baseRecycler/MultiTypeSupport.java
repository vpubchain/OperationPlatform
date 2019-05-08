package com.jiuling.commonbusiness.base.baseRecycler;

/**
 * Created by HP on 2018/4/29.
 */

public interface MultiTypeSupport<T> {

    // 根据当前位置或者条目数据返回布局
    public int getLayoutId(T item, int position);

}
