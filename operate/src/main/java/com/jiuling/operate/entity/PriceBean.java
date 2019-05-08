package com.jiuling.operate.entity;

import java.io.Serializable;
import java.util.List;

public class PriceBean{


    /**
     * id : null
     * name : 维公链主节点
     * detail : 用于搭建维公链主节点服务器
     * createBy : null
     * updateBy : null
     * createTime : null
     * updateTime : null
     * delFlag : null
     * productTypeList : [{"id":2,"productId":null,"name":"主节点一键开通","detail":"一键打包开通主节点服务","type":2,"basePrice":2000,"promotionPrice":1800,"startPromotionTime":1553846492000,"endPromotionTime":1555344000000,"createTime":null,"updateTime":null,"createBy":null,"updateBy":null,"delFlag":null},{"id":7,"productId":null,"name":"主节点一键开通","detail":"打包部署主节点","type":1,"basePrice":200,"promotionPrice":195,"startPromotionTime":1553846541000,"endPromotionTime":1587139200000,"createTime":null,"updateTime":null,"createBy":null,"updateBy":null,"delFlag":null}]
     */

    private Object id;
    private String name;
    private String detail;
    private Object createBy;
    private Object updateBy;
    private Object createTime;
    private Object updateTime;
    private Object delFlag;
    private List<ProductTypeListBean> productTypeList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public List<ProductTypeListBean> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductTypeListBean> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public static class ProductTypeListBean implements Serializable {

        public ProductTypeListBean(int id, Object productId, String name, String detail, int type, double basePrice, double promotionPrice, long startPromotionTime, long endPromotionTime, Object createTime, Object updateTime, Object createBy, Object updateBy, Object delFlag) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.detail = detail;
            this.type = type;
            this.basePrice = basePrice;
            this.promotionPrice = promotionPrice;
            this.startPromotionTime = startPromotionTime;
            this.endPromotionTime = endPromotionTime;
            this.createTime = createTime;
            this.updateTime = updateTime;
            this.createBy = createBy;
            this.updateBy = updateBy;
            this.delFlag = delFlag;
        }

        /**
         * id : 2
         * productId : null
         * name : 主节点一键开通
         * detail : 一键打包开通主节点服务
         * type : 2
         * basePrice : 2000.0
         * promotionPrice : 1800.0
         * startPromotionTime : 1553846492000
         * endPromotionTime : 1555344000000
         * createTime : null
         * updateTime : null
         * createBy : null
         * updateBy : null
         * delFlag : null
         */



        private int id;
        private Object productId;
        private String name;
        private String detail;
        private int type;
        private double basePrice;
        private double promotionPrice;
        private long startPromotionTime;
        private long endPromotionTime;
        private Object createTime;
        private Object updateTime;
        private Object createBy;
        private Object updateBy;
        private Object delFlag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(double basePrice) {
            this.basePrice = basePrice;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public long getStartPromotionTime() {
            return startPromotionTime;
        }

        public void setStartPromotionTime(long startPromotionTime) {
            this.startPromotionTime = startPromotionTime;
        }

        public long getEndPromotionTime() {
            return endPromotionTime;
        }

        public void setEndPromotionTime(long endPromotionTime) {
            this.endPromotionTime = endPromotionTime;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }
    }
}
