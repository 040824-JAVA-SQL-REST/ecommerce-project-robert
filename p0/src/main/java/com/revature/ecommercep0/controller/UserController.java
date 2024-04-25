package com.revature.ecommercep0.controller;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.http.Context;

public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void register(Context ctx) {
        ctx.result("User registered");
        try {
            //First we have to map postman request to our request class

        } catch (Exception e) {
            ctx.status(500); // 500 means internal error means not the clients fault
            e.printStackTrace();

        }
    }

    public void login(Context ctx) {

    }
}
