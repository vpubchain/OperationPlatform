package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateCapitalFlowActivityView;
import com.jiuling.operate.data.OperateCapitalFlowActivityModel;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateCapitalFlowActivityPresenter extends BasePresenter<OperateCapitalFlowActivityModel,IOperateCapitalFlowActivityView> {



    public OperateCapitalFlowActivityPresenter(OperateCapitalFlowActivityModel mainActivityModel, IOperateCapitalFlowActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


}
