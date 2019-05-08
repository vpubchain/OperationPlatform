package com.jiuling.operate.entity;

public class RequestOrderBean {


    /**
     * page : 1
     * limit : 5
     * orderByField : create_time
     * isAsc : false
     * status : null
     * serialNumber : null
     */

    private String page;
    private String limit;
    private String orderByField;
    private String isAsc;
    private String status;
    private String serialNumber;

    public RequestOrderBean(String page, String limit, String orderByField, String isAsc, String status, String serialNumber) {
        this.page = page;
        this.limit = limit;
        this.orderByField = orderByField;
        this.isAsc = isAsc;
        this.status = status;
        this.serialNumber = serialNumber;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public String isIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public Object getStatus() {
        return status;
    }


    public Object getSerialNumber() {
        return serialNumber;
    }

    public String isAsc() {
        return isAsc;
    }
}
