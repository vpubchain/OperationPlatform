package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

public interface IOperateControlActivityView extends IBasicContract.IViewImpl {


    void getUserMasternodeCountBean(UserMasternodeCountBean userMasternodeCountBean);

    void getUserMasternodeIncomeDay(StatisticsIncomeBean statisticsIncomeBean,boolean isDay);
}
