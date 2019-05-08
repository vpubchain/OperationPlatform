package com.jiuling.operate.entity;

public class RequestAlipayBean {

    private String out_trade_no;
    private String subject;
    private String timeout_express;

    public RequestAlipayBean(String out_trade_no, String subject, String timeout_express) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.timeout_express = timeout_express;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }
}
