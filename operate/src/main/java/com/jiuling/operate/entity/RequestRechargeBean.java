package com.jiuling.operate.entity;

public class RequestRechargeBean {

    private String amount;
    private int rechargeWay;

    public RequestRechargeBean(String amount, int rechargeWay) {
        this.amount = amount;
        this.rechargeWay = rechargeWay;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(int rechargeWay) {
        this.rechargeWay = rechargeWay;
    }
}
