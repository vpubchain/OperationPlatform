package com.jiuling.commonbusiness.util.rx.subscriber;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jiuling.commonbusiness.eventbus.FinishMainActivityEvent;
import com.jiuling.commonbusiness.exception.BaseException;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.commonbusiness.util.rx.RxErrorHandler;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;


public abstract  class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {

//    public IBasicContract.IViewImpl mView;
    protected RxErrorHandler mErrorHandler = null;
    protected Context mContext;

    public ErrorHandlerSubscriber(Context context){
        this.mContext = context;
        mErrorHandler = new RxErrorHandler(mContext);
    }




        @Override
    public void onError(Throwable e) {
            Log.i("ttttttttt","开始"+e.toString()+e.getMessage());
        BaseException baseException =  mErrorHandler.handleError(e);
            Log.i("ttttttttt","testcode"+baseException.getCode()+"ccccccccccccc"+e.toString()+"            "+e.getMessage());


        if(baseException==null){
            e.printStackTrace();
            Log.d("ttttttttt",e.getMessage());
        }
        else {
//            mErrorHandler.showErrorMessage(baseException);
            if(baseException.getCode() == BaseException.ERROR_TOKEN){
                EventBus.getDefault().post(new FinishMainActivityEvent());
                toLogin();
            }
        }

        if (e instanceof SocketTimeoutException){

            requestTimeOut();

        }




    }

    protected void requestTimeOut() {

    }

    private void toLogin() {
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        mContext.startActivity(intent);
        //要加一个全局activityfinish
        ARouter.getInstance().build("/ModuleLogin/Login").navigation();
    }




}
