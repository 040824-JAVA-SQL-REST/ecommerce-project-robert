package com.revature.ecommercep0.model;

import java.util.UUID;

public class Cart {
    private String id;
    private String user_id;
    private String total_cost;
    private boolean is_CheckedOut;
    
    
    public Cart(String user_id) { //for making new carts,
        this.id = UUID.randomUUID().toString();
        this.user_id = user_id;
        total_cost = "0.00";
        is_CheckedOut = false;
    }

    public Cart(String id, String user_id, String total_cost, boolean is_CheckedOut) { //when retrieving info from db
        this.id = id;
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.is_CheckedOut = is_CheckedOut;
    }

    public Cart(String user_id, String total_cost) {
        this.id = UUID.randomUUID().toString();
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.is_CheckedOut = false;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getTotal_cost() {
        return total_cost;
    }
    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public boolean isIs_CheckedOut() {
        return is_CheckedOut;
    }

    public void setIs_CheckedOut(boolean is_CheckedOut) {
        this.is_CheckedOut = is_CheckedOut;
    }
    
}
