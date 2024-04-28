package com.revature.ecommercep0.dto.response;

import java.util.List;

import com.revature.ecommercep0.model.Product;

public class AdminOrderItemResponse {
    private String order_id;
    private String customerName;
    private String totalPrice;
    private List<Product> itemsPurchased;
    private String orderStatus;

    public AdminOrderItemResponse(String order_id, String customerName, String totalPrice, String orderStatus) {
        this.order_id = order_id;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
    public AdminOrderItemResponse(String customerName, String totalPrice, List<Product> itemsPurchased,
            String orderStatus) {
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.itemsPurchased = itemsPurchased;
        this.orderStatus = orderStatus;
    }
    
    public AdminOrderItemResponse() {
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    public List<Product> getItemsPurchased() {
        return itemsPurchased;
    }
    public void setItemsPurchased(List<Product> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    
    
}
