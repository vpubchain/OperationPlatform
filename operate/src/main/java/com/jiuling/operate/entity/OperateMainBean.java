package com.jiuling.operate.entity;

import java.util.List;

public class OperateMainBean {


    /**
     * total : 451
     * data : [12,12,12,13,13,5,5,5,4,3,16,17,112,229,232,232,232,305,448,450,450]
     * time : ["2019-03-04","2019-03-05","2019-03-06","2019-03-07","2019-03-08","2019-03-09","2019-03-10","2019-03-11","2019-03-12","2019-03-13","2019-04-01","2019-04-02","2019-04-03","2019-04-04","2019-04-05","2019-04-06","2019-04-07","2019-04-08","2019-04-09","2019-04-10","2019-04-11"]
     */

    private int total;
    private List<Integer> data;
    private List<String> time;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
