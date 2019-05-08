package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;

public interface IOperateRechargeActivityView extends IBasicContract.IViewImpl {


    void getRechargeInfo(String alipayInfo);

}
