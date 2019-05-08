package com.jiuling.operate.presenter;

import android.widget.TextView;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.contract.IOperateOrderFragmentView;
import com.jiuling.operate.data.OperateConfirmBuyActivityModel;
import com.jiuling.operate.data.OperateOrderFragmentModel;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateOrderFragmentPresenter extends BasePresenter<OperateOrderFragmentModel,IOperateOrderFragmentView> {



    public OperateOrderFragmentPresenter(OperateOrderFragmentModel mainActivityModel, IOperateOrderFragmentView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }



}
