package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.PriceBean;

import java.util.List;

public interface IOperatePriceActivityView extends IBasicContract.IViewImpl {


    void getNodePrice(List<PriceBean> priceBeans);

}
