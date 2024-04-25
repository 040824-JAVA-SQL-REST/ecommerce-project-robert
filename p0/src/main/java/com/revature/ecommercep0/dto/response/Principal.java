package com.revature.ecommercep0.dto.response;

import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;

public class Principal {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;

    public Principal() {
    }
    public Principal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFname();
        this.lastName = user.getLname();
        this.role = user.getRole();
    }
    public Principal(String id, String email, String firstName, String lastName, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
}
