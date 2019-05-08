package com.jiuling.operate.entity;

public class RequestOrderNumberBean {

    //product_type_id: 7, quantity: 1, price: 1
    private int product_type_id;
    private String quantity;
    private String price;

    public RequestOrderNumberBean(int product_type_id, String quantity, String price) {
        this.product_type_id = product_type_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(int product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
