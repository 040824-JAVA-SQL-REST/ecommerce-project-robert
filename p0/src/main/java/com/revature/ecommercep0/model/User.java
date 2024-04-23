package com.revature.ecommercep0.model;

import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String role_id; //Foreign key to tie to roles table

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRole_id() {
        return role_id;
    }
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
    public User(String email, String fname, String lname, String password) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.role_id = "USER";
    }
    public User() {
        this.id = UUID.randomUUID().toString();
        this.role_id = "USER";
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
