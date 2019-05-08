package com.vpubchain.wallet.exception;


public class BaseException extends Exception {

    /*API错误*/
    public static final int API_ERROR = 0x0;

    /*网络错误*/
    public static final int NETWORD_ERROR = 0x1;
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*json错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x4;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x5;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 0x6;

    /*连接网络超时*/
    public static final int SOCKET_TIMEOUT_ERROR = 0x7;

    /*无网络连接*/
    public static final int SOCKET_ERROR = 0x8;




    //    api /////////////////////////////////////////

//    // 服务器错误
//    public static final int  ERROR_API_SYSTEM=10000;

//    // 登录错误，用户名密码错误
//    public static final int  ERROR_API_LOGIN=10001;
//
//    //调用无权限的API
//    public static final int  ERROR_API_NO_PERMISSION=10002;
//
//    //账户被冻结
//    public static final int  ERROR_API_ACCOUNT_FREEZE=10003;






    // http

    public static final int ERROR_HTTP_400=400;

    public static final int ERROR_HTTP_404=404;

    public static final int ERROR_HTTP_405=405;

    public static final int ERROR_HTTP_500=500;




    //**************************************************


    //请求方式错误
    public static final int ERROR_REQUEST=0;

    //用户名或者密码错误
    public static final int ERROR_NAMEORPASSWORD=4;
    //参数异常，请检查请求参数
    public static final int ERROR_PARAM=4044;
    //Token 失效
    public static final int  ERROR_TOKEN=10000;
    //操作失败
    public static final int ERROR_OPERATION=-1;
    //订单不存在
    public static final int ERROR_NO_ORDER=101;
    //该订单接单时间已超时
    public static final int ERROR_TIME_OUT=102;
    //该服务商绑定电话不存在
    public static final int ERROR_NO_PHONE=201;
    //该服务商没有服务项目
    public static final int ERROR_NO_PROJECT=202;
    //该员工不存在或已下线或被指派
    public static final int ERROR_NO_STAFF=301;
    //该员工信息有误
    public static final int ERROR_ERROR_STAFF=302;
    //绑定隐私号有误
    public static final int ERROR_ERROR_BIND=1001;
    //号码不存在
    public static final int ERROR_NO_NUMBER=1002;
    //无可用号码
    public static final int ERROR_NO_USE_NUMBER=1003;
    //号码格式非法
    public static final int ERROR_ERROR_FORMAT_NUMBER=1004;
    //提现金额超出
    public static final int ERROR_OUT_OF=1401;


    //当前账户费用不足
    public static final int ERROR_NOENOUGHMONEY=50000;

    //uxto 已经被花费
    public static final int ERROR_UTXO_USE=50001;





    private int code;

    private String displayMessage;

    public BaseException() {
    }

    public BaseException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
