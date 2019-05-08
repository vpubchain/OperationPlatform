package com.jiuling.operate.presenter;

import android.widget.TextView;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateMyNodeActivityView;
import com.jiuling.operate.data.OperateMyNodeActivityModel;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateMyNodeActivityPresenter extends BasePresenter<OperateMyNodeActivityModel,IOperateMyNodeActivityView> {



    public OperateMyNodeActivityPresenter(OperateMyNodeActivityModel mainActivityModel, IOperateMyNodeActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


}
