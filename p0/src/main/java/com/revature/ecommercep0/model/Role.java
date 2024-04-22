package com.revature.ecommercep0.model;

import java.util.UUID;

public class Role {
    private String role_id;
    private String name;
    
    public Role() {
    }
    public Role(String name) {
        this.role_id = UUID.randomUUID().toString();
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole_id() {
        return role_id;
    }
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
