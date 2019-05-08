package com.jiuling.operate.entity;

import java.io.Serializable;
import java.util.List;

public class RenewNodeBean implements Serializable {


    /**
     * product : {"id":1,"name":"维公链主节点","detail":"用于搭建维公链主节点服务器","createBy":1,"updateBy":1,"createTime":1550655238000,"updateTime":1553845980000,"delFlag":"0"}
     * typList : [{"price":2000,"dtl":{"id":2,"productId":1,"name":"主节点一键开通","detail":"一键打包开通主节点服务","type":2,"basePrice":2000,"promotionPrice":1800,"startPromotionTime":1553846492000,"endPromotionTime":1555344000000,"createTime":1550712084000,"updateTime":1553846503000,"createBy":1,"updateBy":1,"delFlag":"0"}},{"price":0.01,"dtl":{"id":7,"productId":1,"name":"主节点一键开通","detail":"打包部署主节点","type":1,"basePrice":200,"promotionPrice":0.01,"startPromotionTime":1553846541000,"endPromotionTime":1587139200000,"createTime":1551076111000,"updateTime":1555310079000,"createBy":1,"updateBy":1,"delFlag":"0"}}]
     */

    private ProductBean product;
    private List<TypListBean> typList;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public List<TypListBean> getTypList() {
        return typList;
    }

    public void setTypList(List<TypListBean> typList) {
        this.typList = typList;
    }

    public static class ProductBean implements Serializable {
        /**
         * id : 1
         * name : 维公链主节点
         * detail : 用于搭建维公链主节点服务器
         * createBy : 1
         * updateBy : 1
         * createTime : 1550655238000
         * updateTime : 1553845980000
         * delFlag : 0
         */

        private int id;
        private String name;
        private String detail;
        private int createBy;
        private int updateBy;
        private long createTime;
        private long updateTime;
        private String delFlag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public int getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(int updateBy) {
            this.updateBy = updateBy;
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

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }

    public static class TypListBean implements Serializable {
        /**
         * price : 2000.0
         * dtl : {"id":2,"productId":1,"name":"主节点一键开通","detail":"一键打包开通主节点服务","type":2,"basePrice":2000,"promotionPrice":1800,"startPromotionTime":1553846492000,"endPromotionTime":1555344000000,"createTime":1550712084000,"updateTime":1553846503000,"createBy":1,"updateBy":1,"delFlag":"0"}
         */

        private double price;
        private DtlBean dtl;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public DtlBean getDtl() {
            return dtl;
        }

        public void setDtl(DtlBean dtl) {
            this.dtl = dtl;
        }

        public static class DtlBean implements Serializable{
            /**
             * id : 2
             * productId : 1
             * name : 主节点一键开通
             * detail : 一键打包开通主节点服务
             * type : 2
             * basePrice : 2000.0
             * promotionPrice : 1800.0
             * startPromotionTime : 1553846492000
             * endPromotionTime : 1555344000000
             * createTime : 1550712084000
             * updateTime : 1553846503000
             * createBy : 1
             * updateBy : 1
             * delFlag : 0
             */

            private int id;
            private int productId;
            private String name;
            private String detail;
            private int type;
            private double basePrice;
            private double promotionPrice;
            private long startPromotionTime;
            private long endPromotionTime;
            private long createTime;
            private long updateTime;
            private int createBy;
            private int updateBy;
            private String delFlag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
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

            public int getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(int updateBy) {
                this.updateBy = updateBy;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }
        }
    }
}
