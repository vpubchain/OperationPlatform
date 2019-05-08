package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateCapitalFlowFragmentView;
import com.jiuling.operate.contract.IOperateNodeFragmentView;
import com.jiuling.operate.data.OperateCapitalFlowFragmentModel;
import com.jiuling.operate.data.OperateNodeFragmentModel;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateCapitalFlowFragmentPresenter extends BasePresenter<OperateCapitalFlowFragmentModel,IOperateCapitalFlowFragmentView> {



    public OperateCapitalFlowFragmentPresenter(OperateCapitalFlowFragmentModel mainActivityModel, IOperateCapitalFlowFragmentView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }



}
