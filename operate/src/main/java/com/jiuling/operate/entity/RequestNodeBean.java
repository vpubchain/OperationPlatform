package com.jiuling.operate.entity;

public class RequestNodeBean {


    /**
     * page : 1
     * limit : 5
     * orderByField : create_time
     * isAsc : false
     * nodeStatus : NOTENABLED
     * node_name : null
     */

    private String page;
    private String limit;
    private String orderByField;
    private String isAsc;
    private String nodeStatus;
    private String node_name;

    public RequestNodeBean(String page, String limit, String orderByField, String isAsc, String nodeStatus, String node_name) {
        this.page = page;
        this.limit = limit;
        this.orderByField = orderByField;
        this.isAsc = isAsc;
        this.nodeStatus = nodeStatus;
        this.node_name = node_name;
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

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }
}
