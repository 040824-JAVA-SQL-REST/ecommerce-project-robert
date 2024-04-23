package com.revature.ecommercep0.model;

public class CartHistory {
    private String cart_id;
    private String product_id;
    private String quantity;

    public CartHistory(String cart_id, String product_id, String quantity) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }
    public String getCart_id() {
        return cart_id;
    }
    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
    public String getProduct_id() {
        return product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    
    
    
}
