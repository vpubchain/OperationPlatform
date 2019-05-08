package com.jiuling.operate.entity;

public class SetUserInfoBean {


    /**
     * name : 张文彬
     * phone : 18820785560
     * email : 1757775921@qq.com
     */




    private String name;
    private String phone;
    private String email;

    public SetUserInfoBean(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
