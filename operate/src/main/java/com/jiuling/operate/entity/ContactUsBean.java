package com.jiuling.operate.entity;

public class ContactUsBean {


    /**
     * userName : ff
     * userTel : ff
     * userEmail : ff
     * askContent : ff
     */

    private String userName;
    private String userTel;
    private String userEmail;
    private String askContent;

    public ContactUsBean(String userName, String userTel, String userEmail, String askContent) {
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.askContent = askContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAskContent() {
        return askContent;
    }

    public void setAskContent(String askContent) {
        this.askContent = askContent;
    }
}
