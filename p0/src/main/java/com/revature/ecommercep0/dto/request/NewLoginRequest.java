package com.revature.ecommercep0.dto.request;

public class NewLoginRequest {
    private String email;
    private String password;
    public NewLoginRequest() {
    }
    public NewLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
