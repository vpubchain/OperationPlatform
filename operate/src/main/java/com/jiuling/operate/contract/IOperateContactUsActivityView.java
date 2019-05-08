package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

public interface IOperateContactUsActivityView extends IBasicContract.IViewImpl {


    void contactUs(Boolean result);

}
