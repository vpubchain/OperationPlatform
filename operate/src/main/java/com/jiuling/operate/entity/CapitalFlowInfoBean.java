package com.jiuling.operate.entity;

import java.util.List;

public class CapitalFlowInfoBean {


    /**
     * total : 5
     * size : 8
     * pages : 1
     * current : 1
     * records : [{"serialNumber":"OD2019040313403966003841147","amount":3900,"payWay":3,"type":"2","createTime":1554270043000},{"serialNumber":"OD2019040313402640603386602","amount":3900,"payWay":3,"type":"2","createTime":1554270029000},{"serialNumber":"OD2019040313401339503111066","amount":3900,"payWay":3,"type":"2","createTime":1554270016000},{"serialNumber":"OD2019040313400140003778064","amount":3900,"payWay":3,"type":"2","createTime":1554270004000},{"serialNumber":"OD2019040313394255003618325","amount":3900,"payWay":3,"type":"2","createTime":1554269988000}]
     */

    private int total;
    private int size;
    private int pages;
    private int current;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * serialNumber : OD2019040313403966003841147
         * amount : 3900.0
         * payWay : 3
         * type : 2
         * createTime : 1554270043000
         */

        private String serialNumber;
        private double amount;
        private int payWay;
        private String type;
        private long createTime;

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
