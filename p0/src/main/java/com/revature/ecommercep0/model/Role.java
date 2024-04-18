package com.revature.ecommercep0.model;

public class Role {
    private String role_id;
    private String name;
    
    
    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
