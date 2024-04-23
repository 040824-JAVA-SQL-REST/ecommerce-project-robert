package com.revature.ecommercep0.controller;
import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.http.Context;

public class UserController {
    public void register(Context ctx) {
        ctx.result("User registered");
        try {

        } catch (Exception e) {
            ctx.status(500);
            
        }
    }
    
}
