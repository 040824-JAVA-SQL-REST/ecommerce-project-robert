package com.revature.ecommercep0.model;

public class OrderHistory {
    private String orderId;
    private String productId;
    private String quantity;
    
    public OrderHistory() {
    }
    public OrderHistory(String orderId, String productId, String quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    

    
    
}
