package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.NodeDetailsBean;

public interface IOperateConfirmBuyActivityView extends IBasicContract.IViewImpl {


    void getBuyCount(int i);

    void getNodeDetailById(NodeDetailsBean nodeDetailsBean);

}
