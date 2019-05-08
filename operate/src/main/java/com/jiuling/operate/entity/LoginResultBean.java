package com.jiuling.operate.entity;

public class LoginResultBean {


    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSBqbCIsInBob25lIjoiMTg4MjA3ODU1NjAiLCJ1c2VyX25hbWUiOiJmZmQyMmVjMC0wMzVlLTQwMWYtOWFhMy1jM2NlNjliYmUwMmIiLCJzY29wZSI6WyJzZXJ2ZXIiXSwiZXhwIjoxNTU0MTI1NjcwLCJ1c2VySWQiOjY3LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZmUyOWM3ZWItY2I4MS00ZjI4LTgxZGMtZGQ2N2RjZTY0NzUwIiwiY2xpZW50X2lkIjoidnAifQ.19k09KjvLeBX5JF5Ij7NRxlvv-Y9ybsgt3Wl5RrwHGY
     * token_type : bearer
     * refresh_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSBqbCIsInBob25lIjoiMTg4MjA3ODU1NjAiLCJ1c2VyX25hbWUiOiJmZmQyMmVjMC0wMzVlLTQwMWYtOWFhMy1jM2NlNjliYmUwMmIiLCJzY29wZSI6WyJzZXJ2ZXIiXSwiYXRpIjoiZmUyOWM3ZWItY2I4MS00ZjI4LTgxZGMtZGQ2N2RjZTY0NzUwIiwiZXhwIjoxNTU2Njc0NDcwLCJ1c2VySWQiOjY3LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZTkzYjU0NGYtYmFkOC00NjQ1LWEyZTQtOTBmMmVmMTM5MjRiIiwiY2xpZW50X2lkIjoidnAifQ.GELb4vMb-8zVWqOIPdgGi_gUhfQsVLuzLhsliamte6Y
     * expires_in : 43199
     * scope : server
     * license : made by jl
     * userId : 67
     * phone : 18820785560
     * jti : fe29c7eb-cb81-4f28-81dc-dd67dce64750
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String license;
    private int userId;
    private String phone;
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
