package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateMyOrderActivityView;
import com.jiuling.operate.data.OperateMyOrderActivityModel;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateMyOrderActivityPresenter extends BasePresenter<OperateMyOrderActivityModel,IOperateMyOrderActivityView> {



    public OperateMyOrderActivityPresenter(OperateMyOrderActivityModel mainActivityModel, IOperateMyOrderActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


}
