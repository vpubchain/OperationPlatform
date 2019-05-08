package com.jiuling.commonbusiness.entity;

/**
 * 基础Response，需要与后端配合
 */

public class BaseResponse<T> {


    /**
     * Data : {"accessToken":"at.7jrcjmna8qnqg8d3dgnzs87m4v2dme3l-32enpqgusd-1jvdfe4-uxo15ik0s","expireTime":1470810222045}
     * code : 1
     * msg : 操作成功!
     */


    private int code;
    private boolean success;
    private String msg = "";
    private T data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess(){
        return success;
    }


    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == 0;
    }



    @Override
    public String toString() {
        return "BaseResponse{" +
                "data=" + data +
                ", status=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }


//
//    private int ret;
//    private String msg = "";
//    private T data;
//
//    public int getStatus() {
//        return ret;
//    }
//
//    public void setStatus(int status) {
//        this.ret = status;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public int getCode() {
//        return ret;
//    }
//
//    public void setCode(int code) {
//        this.ret = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public boolean isSuccess() {
//        return ret == 0;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "BaseResponse{" +
//                "data=" + data +
//                ", status=" + ret +
//                ", msg='" + msg + '\'' +
//                '}';
//    }
}
