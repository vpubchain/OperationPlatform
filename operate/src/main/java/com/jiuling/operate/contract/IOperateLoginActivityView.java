package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;

public interface IOperateLoginActivityView extends IBasicContract.IViewImpl {


    void getLoginCode();

    void loginSuccess();

}
