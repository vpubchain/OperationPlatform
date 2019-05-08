package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.RenewNodeBean;

public interface IOperateNodeFragmentView extends IBasicContract.IViewImpl {

    void getMasterNodeInfo(MasterNodeBean masterNodeBean);

    void getUserOrderInfo(OrderInfoBean orderInfoBean);

    void getCapitalFlowInfo(CapitalFlowInfoBean capitalFlowInfoBean);

    void installTrusteeshipNode(Boolean installTrusteeshipNodeResult);

    void nodeRenew(RenewNodeBean renewNodeBean);

    void cancleOrder(boolean b);

}
