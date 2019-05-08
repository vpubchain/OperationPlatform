package com.jiuling.operate.entity;

import java.util.List;

public class OrderInfoBean {


    /**
     * total : 11
     * size : 5
     * pages : 3
     * current : 1
     * records : [{"serialNumber":"OD2019040313403966003841147","productTypeId":7,"productPrice":195,"productDesc":"主节点一键开通-打包部署主节点","productPriceType":1,"quantity":20,"amount":3900,"payWay":3,"isRenew":"N","renewMid":null,"status":1,"createTime":1554270039000,"updateTime":1554270043000,"createBy":67,"delFlag":"0"},{"serialNumber":"OD2019040313402640603386602","productTypeId":7,"productPrice":195,"productDesc":"主节点一键开通-打包部署主节点","productPriceType":1,"quantity":20,"amount":3900,"payWay":3,"isRenew":"N","renewMid":null,"status":1,"createTime":1554270026000,"updateTime":1554270029000,"createBy":67,"delFlag":"0"},{"serialNumber":"OD2019040313401339503111066","productTypeId":7,"productPrice":195,"productDesc":"主节点一键开通-打包部署主节点","productPriceType":1,"quantity":20,"amount":3900,"payWay":3,"isRenew":"N","renewMid":null,"status":1,"createTime":1554270013000,"updateTime":1554270016000,"createBy":67,"delFlag":"0"},{"serialNumber":"OD2019040313400140003778064","productTypeId":7,"productPrice":195,"productDesc":"主节点一键开通-打包部署主节点","productPriceType":1,"quantity":20,"amount":3900,"payWay":3,"isRenew":"N","renewMid":null,"status":1,"createTime":1554270001000,"updateTime":1554270004000,"createBy":67,"delFlag":"0"},{"serialNumber":"OD2019040313394255003618325","productTypeId":7,"productPrice":195,"productDesc":"主节点一键开通-打包部署主节点","productPriceType":1,"quantity":20,"amount":3900,"payWay":3,"isRenew":"N","renewMid":null,"status":1,"createTime":1554269982000,"updateTime":1554269988000,"createBy":67,"delFlag":"0"}]
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
         * productTypeId : 7
         * productPrice : 195.0
         * productDesc : 主节点一键开通-打包部署主节点
         * productPriceType : 1
         * quantity : 20
         * amount : 3900.0
         * payWay : 3
         * isRenew : N
         * renewMid : null
         * status : 1
         * createTime : 1554270039000
         * updateTime : 1554270043000
         * createBy : 67
         * delFlag : 0
         */

        private String serialNumber;
        private int productTypeId;
        private double productPrice;
        private String productDesc;
        private int productPriceType;
        private int quantity;
        private double amount;
        private int payWay;
        private String isRenew;
        private Object renewMid;
        private int status;
        private long createTime;
        private long updateTime;
        private int createBy;
        private String delFlag;

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public int getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(int productTypeId) {
            this.productTypeId = productTypeId;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public int getProductPriceType() {
            return productPriceType;
        }

        public void setProductPriceType(int productPriceType) {
            this.productPriceType = productPriceType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
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

        public String getIsRenew() {
            return isRenew;
        }

        public void setIsRenew(String isRenew) {
            this.isRenew = isRenew;
        }

        public Object getRenewMid() {
            return renewMid;
        }

        public void setRenewMid(Object renewMid) {
            this.renewMid = renewMid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }

    @Override
    public String toString() {
        return "OrderInfoBean{" +
                "total=" + total +
                ", size=" + size +
                ", pages=" + pages +
                ", current=" + current +
                ", records=" + records.size() +
                '}';
    }
}
