package com.jiuling.operate.contract;

import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.operate.entity.PersonalInfoBean;

public interface IOperateInformationActivityView extends IBasicContract.IViewImpl {


    void getPersonalInfo(PersonalInfoBean personalInfoBean);

    void setPersonalInfo(Boolean result);

    void getChangePhoneCode(Boolean result);

    void changePhone(Boolean result);
}
