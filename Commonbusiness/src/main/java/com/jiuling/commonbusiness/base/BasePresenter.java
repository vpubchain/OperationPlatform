package com.jiuling.commonbusiness.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.jiuling.commonbusiness.contract.IBasicContract;


/**
 * Created by Ivan on 2017/1/3.
 */

public class BasePresenter<M,V extends IBasicContract.IViewImpl> {



    protected M mModel;

    protected V mView;


    protected Context mContext;


    public BasePresenter(M m, V v){

        this.mModel=m;
        this.mView = v;

        initContext();

    }



    private void initContext(){



        if(mView instanceof Fragment){
            mContext = ((Fragment)mView).getActivity();
        }
        else {
            mContext = (Activity) mView;
        }
    }




}
