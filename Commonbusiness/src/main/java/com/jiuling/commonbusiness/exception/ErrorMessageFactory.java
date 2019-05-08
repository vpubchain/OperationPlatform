package com.jiuling.commonbusiness.exception;

import android.content.Context;
import android.util.Log;

import com.jiuling.commonbusiness.R;


public class ErrorMessageFactory {

    public static String create(Context context, int code) {
        Log.i("test","code"+code);
        String errorMsg = null;
        switch (code) {
            case 0:
                errorMsg = context.getResources().getString(R.string.error_http_404);
                break;
            case BaseException.HTTP_ERROR:
                errorMsg = context.getResources().getString(R.string.error_http);
                break;
            case BaseException.SOCKET_TIMEOUT_ERROR:
                errorMsg = context.getResources().getString(R.string.error_socket_timeout);
                break;
            case BaseException.SOCKET_ERROR:
                errorMsg = context.getResources().getString(R.string.error_socket_unreachable);
                break;
            case BaseException.ERROR_HTTP_400:
                errorMsg = context.getResources().getString(R.string.error_http_400);
                break;
            case BaseException.ERROR_HTTP_404:
                errorMsg = context.getResources().getString(R.string.error_http_404);
                break;
            case BaseException.ERROR_HTTP_500:
                errorMsg = context.getResources().getString(R.string.error_http_500);
                break;
            case ApiException.ERROR_TOKEN:
                errorMsg = context.getResources().getString(R.string.error_token);
                break;
            case ApiException.ERROR_NAMEORPASSWORD:
                errorMsg ="网络错误";
                break;
            case ApiException.ERROR_OPERATION:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_PARAM:
                errorMsg="参数异常，请检查请求参数";
                break;
            case ApiException.ERROR_NO_ORDER:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_NO_STAFF:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_TIME_OUT:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_NO_PHONE:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_NO_PROJECT:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_ERROR_STAFF:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_ERROR_BIND:
                errorMsg="网络错误";
                break;

            case ApiException.ERROR_NO_NUMBER:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_NO_USE_NUMBER:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_ERROR_FORMAT_NUMBER:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_OUT_OF:
                errorMsg="网络错误";
                break;
            case ApiException.ERROR_NOENOUGHMONEY:
                errorMsg="当前账号费用不足";
                break;
            case ApiException.ERROR_UTXO_USE:
                errorMsg = context.getResources().getString(R.string.error_unkown);
                break;
            default:
                errorMsg = context.getResources().getString(R.string.error_unkown);
                break;
        }
        return errorMsg;
    }
}
