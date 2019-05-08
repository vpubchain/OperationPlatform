package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.OrderNumberBean;

public interface IOperatePaymentActivityView extends IBasicContract.IViewImpl {


    void getOrderNubmer(OrderNumberBean orderNumberBean);

    void getAlipayInfo(String alipayInfo);

    void balancePaymentResult(String result);
}
