package com.jiuling.operate.data;


import android.util.Log;

import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.PersonalInfoBean;
import com.jiuling.operate.entity.RequestChangePhoneBean;
import com.jiuling.operate.entity.SetUserInfoBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import io.reactivex.Observable;
import retrofit2.http.Header;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateInformationActivityModel {

    private ApiService mApiService;

    public OperateInformationActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }

    public Observable<BaseResponse<PersonalInfoBean>> getPersonalInfo(String authorization){


        return  mApiService.getPersonalInfo(authorization);
    }


    public Observable<BaseResponse<Boolean>> setPersonalInfo(String authorization, SetUserInfoBean setUserInfoBean){


        return  mApiService.setPersonalInfo(authorization,setUserInfoBean);
    }


    public Observable<BaseResponse<Boolean>> getChangePhoneCode(String authorization,String mobileNumber){

        Log.i("body","getChangePhoneCode"+mobileNumber);
        return  mApiService.getChangePhoneCode(authorization,"smsCode/phone/change/"+ mobileNumber);
    }

    public Observable<BaseResponse<Boolean>> changePhone(String authorization, String code, RequestChangePhoneBean requestChangePhoneBean){

        return  mApiService.changePhone(authorization,"user/phone?code="+code,requestChangePhoneBean);
    }
}
