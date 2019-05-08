package com.jiuling.operate.entity;

import java.util.List;

public class MasterNodeBean {


    /**
     * total : 89
     * size : 5
     * pages : 18
     * current : 1
     * records : [{"surplusTime":2510351722,"dtl":{"id":854,"uid":67,"orderNo":"OD2019041711302133603589060","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MNBOsYqgw0","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558063824000,"createTime":1555471824000,"updateTime":null,"delFlag":"0","isEntrust":"0"}},{"surplusTime":2509029722,"dtl":{"id":853,"uid":67,"orderNo":"OD2019041711082045203455465","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MNyaLZozSJ","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558062502000,"createTime":1555470502000,"updateTime":null,"delFlag":"0","isEntrust":"0"}},{"surplusTime":2507168722,"dtl":{"id":852,"uid":67,"orderNo":"OD2019041710371678503231573","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MN3RAbmcVa","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558060641000,"createTime":1555468641000,"updateTime":null,"delFlag":"0","isEntrust":"0"}},{"surplusTime":2506940722,"dtl":{"id":851,"uid":67,"orderNo":"OD2019041710332881003190779","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MNi0gdkHyg","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558060413000,"createTime":1555468413000,"updateTime":null,"delFlag":"0","isEntrust":"0"}},{"surplusTime":2506791722,"dtl":{"id":850,"uid":67,"orderNo":"OD2019041710304397103765142","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MNwKDOvGgE","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558060264000,"createTime":1555468264000,"updateTime":null,"delFlag":"0","isEntrust":"0"}}]
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
         * surplusTime : 2510351722
         * dtl : {"id":854,"uid":67,"orderNo":"OD2019041711302133603589060","ip":null,"vpsid":null,"vpsType":null,"vpsProvider":null,"nodeName":"MNBOsYqgw0","genkey":null,"account":null,"status":null,"step":0,"txid":null,"txindex":null,"maturityTime":1558063824000,"createTime":1555471824000,"updateTime":null,"delFlag":"0","isEntrust":"0"}
         */

        private long surplusTime;
        private DtlBean dtl;

        public long getSurplusTime() {
            return surplusTime;
        }

        public void setSurplusTime(long surplusTime) {
            this.surplusTime = surplusTime;
        }

        public DtlBean getDtl() {
            return dtl;
        }

        public void setDtl(DtlBean dtl) {
            this.dtl = dtl;
        }

        public static class DtlBean {
            /**
             * id : 854
             * uid : 67
             * orderNo : OD2019041711302133603589060
             * ip : null
             * vpsid : null
             * vpsType : null
             * vpsProvider : null
             * nodeName : MNBOsYqgw0
             * genkey : null
             * account : null
             * status : null
             * step : 0
             * txid : null
             * txindex : null
             * maturityTime : 1558063824000
             * createTime : 1555471824000
             * updateTime : null
             * delFlag : 0
             * isEntrust : 0
             */

            private int id;
            private int uid;
            private String orderNo;
            private String ip;
            private String vpsid;
            private String vpsType;
            private String vpsProvider;
            private String nodeName;
            private String genkey;
            private String account;
            private String status;
            private int step;
            private String txid;
            private String txindex;
            private long maturityTime;
            private long createTime;
            private String updateTime;
            private String delFlag;
            private int isEntrust;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getVpsid() {
                return vpsid;
            }

            public void setVpsid(String vpsid) {
                this.vpsid = vpsid;
            }

            public String getVpsType() {
                return vpsType;
            }

            public void setVpsType(String vpsType) {
                this.vpsType = vpsType;
            }

            public String getVpsProvider() {
                return vpsProvider;
            }

            public void setVpsProvider(String vpsProvider) {
                this.vpsProvider = vpsProvider;
            }

            public String getNodeName() {
                return nodeName;
            }

            public void setNodeName(String nodeName) {
                this.nodeName = nodeName;
            }

            public String getGenkey() {
                return genkey;
            }

            public void setGenkey(String genkey) {
                this.genkey = genkey;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getStep() {
                return step;
            }

            public void setStep(int step) {
                this.step = step;
            }

            public String getTxid() {
                return txid;
            }

            public void setTxid(String txid) {
                this.txid = txid;
            }

            public String getTxindex() {
                return txindex;
            }

            public void setTxindex(String txindex) {
                this.txindex = txindex;
            }

            public long getMaturityTime() {
                return maturityTime;
            }

            public void setMaturityTime(long maturityTime) {
                this.maturityTime = maturityTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public int getIsEntrust() {
                return isEntrust;
            }

            public void setIsEntrust(int isEntrust) {
                this.isEntrust = isEntrust;
            }
        }
    }
}
