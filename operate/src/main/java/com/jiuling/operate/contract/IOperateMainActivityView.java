package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.OperateMainBean;

public interface IOperateMainActivityView extends IBasicContract.IViewImpl {

    void getMasternodeCount(OperateMainBean operateMainBean);


}
