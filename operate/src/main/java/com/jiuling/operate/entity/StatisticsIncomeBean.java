package com.jiuling.operate.entity;

import java.util.List;

public class StatisticsIncomeBean {
    /**
     * amount : 160315.65
     * amountToday : null
     * data : [1470.15,15267.78,21819.6,22738.32,22278.96,23554.08,30976.11,22210.65]
     * time : ["2019-04-03","2019-04-04","2019-04-05","2019-04-06","2019-04-07","2019-04-08","2019-04-09","2019-04-10"]
     */

    private double amount;
    private double amountToday;
    private List<Double> data;
    private List<String> time;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Object getAmountToday() {
        return amountToday;
    }

    public void setAmountToday(double amountToday) {
        this.amountToday = amountToday;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }


//    /**
//     * amount : null
//     * amountToday : null
//     * data : []
//     * time : []
//     */
//
//    private Object amount;
//    private Object amountToday;
//    private List<?> data;
//    private List<?> time;
//
//    public Object getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Object amount) {
//        this.amount = amount;
//    }
//
//    public Object getAmountToday() {
//        return amountToday;
//    }
//
//    public void setAmountToday(Object amountToday) {
//        this.amountToday = amountToday;
//    }
//
//    public List<?> getData() {
//        return data;
//    }
//
//    public void setData(List<?> data) {
//        this.data = data;
//    }
//
//    public List<?> getTime() {
//        return time;
//    }
//
//    public void setTime(List<?> time) {
//        this.time = time;
//    }
//
//    @Override
//    public String toString() {
//        return "StatisticsIncomeBean{" +
//                "amount=" + amount +
//                ", amountToday=" + amountToday +
//                ", data=" + data +
//                ", time=" + time +
//                '}';
//    }
}
