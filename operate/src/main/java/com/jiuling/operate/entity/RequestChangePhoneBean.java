package com.jiuling.operate.entity;

public class RequestChangePhoneBean {


    /**
     * oldPhone : 18820785560
     * newPhone : 18820785560
     */

    private String oldPhone;
    private String newPhone;

    public RequestChangePhoneBean(String oldPhone, String newPhone) {
        this.oldPhone = oldPhone;
        this.newPhone = newPhone;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }
}
