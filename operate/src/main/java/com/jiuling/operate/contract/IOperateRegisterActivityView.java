package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.base.recyclerbase.BaseRecyclerBean;
import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.commonbusiness.entity.BaseResponse;

import io.reactivex.Observable;

public interface IOperateRegisterActivityView extends IBasicContract.IViewImpl {



    void getRegisterCode();


    void registerSuccess(boolean result);

    void checkInfoResult(boolean success, String msg);

}
