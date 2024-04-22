package com.revature.ecommercep0.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private String id;
    private List<Product> products; // Many-to-Many relationship with Product
    private String totalPrice;
    private String status;
    private Date orderDate;
    private String user_id; // Many-to-One relationship with User

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderDate = new Date(0);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
