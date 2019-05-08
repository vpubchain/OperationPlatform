package com.jiuling.commonbusiness.util.rx;

import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jiuling.commonbusiness.exception.ApiException;
import com.jiuling.commonbusiness.exception.BaseException;
import com.jiuling.commonbusiness.exception.ErrorMessageFactory;

import java.net.SocketException;
import java.net.SocketTimeoutException;


public class RxErrorHandler {

    private Context mContext;
    public RxErrorHandler(Context context){
        this.mContext = context;
    }

    public BaseException handleError(Throwable e){

        BaseException exception = new BaseException();
        if (e instanceof JsonParseException){
            exception.setCode(BaseException.JSON_ERROR);
        }
        else  if(e instanceof HttpException){
            exception.setCode(((HttpException)e).code());
        }
        else  if(e instanceof SocketTimeoutException){
            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        }
        else if(e instanceof SocketException){

        }
        else if(e instanceof ApiException){
            exception.setCode(((ApiException)e).getCode());
        }
        else {
            exception.setCode(BaseException.UNKNOWN_ERROR);
        }

//        if (exception instanceof  ApiException){
//            exception.getDisplayMessage()
//        }
//        exception.setDisplayMessage(exception.getDisplayMessage());

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext,exception.getCode()));
        return  exception;
    }

    public void  showErrorMessage(BaseException e){
        Toast.makeText(mContext,e.getDisplayMessage(), Toast.LENGTH_LONG).show();

    }
}
