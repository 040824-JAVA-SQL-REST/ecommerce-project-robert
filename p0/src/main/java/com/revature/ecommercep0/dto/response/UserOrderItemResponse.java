package com.revature.ecommercep0.dto.response;

import java.sql.Date;

public class UserOrderItemResponse {
    private String orderId;
    private String productName;
    private String quantity;
    private String totalCost;
    private Date orderDate;
    private String orderStatus;
    public UserOrderItemResponse() {
    }
    public UserOrderItemResponse(String orderId, String productName, String quantity, String totalCost, Date orderDate,
            String orderStatus) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
}
