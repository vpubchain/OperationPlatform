package com.jiuling.operate.entity;

public class NodeDetailsBean {


    /**
     * product : {"id":1,"name":"维公链主节点","detail":"用于搭建维公链主节点服务器","createBy":1,"updateBy":1,"createTime":1550655238000,"updateTime":1553845980000,"delFlag":"0"}
     * price : 1800.0
     * type : {"id":2,"productId":1,"name":"主节点一键开通","detail":"一键打包开通主节点服务","type":2,"basePrice":2000,"promotionPrice":1800,"startPromotionTime":1553846492000,"endPromotionTime":1555344000000,"createTime":1550712084000,"updateTime":1553846503000,"createBy":1,"updateBy":1,"delFlag":"0"}
     */

    private ProductBean product;
    private double price;
    private TypeBean type;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
    }

    public static class ProductBean {
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

    public static class TypeBean {
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
