package com.revature.ecommercep0.controller;

import java.util.HashMap;
import java.util.Map;

import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.TokenService;

import io.javalin.http.Context;

public class ProductController {
    private final ProductService productService;
    private final TokenService tokenService;

    public ProductController(ProductService productService, TokenService tokenService) {
        this.productService = productService;
        this.tokenService = tokenService;
    }

    public void addProductToCatalog(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            String token = ctx.header("auth-token");

            if (token == null || token.isEmpty()) {
                ctx.status(401); // Unauthorized
                errors.put("Error: ", "Your token is not valid");
                ctx.json(errors);
                return;
            }

            // Now we parse the token to get the principal(auth)
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error: ", "Your token is not valid");
                return;
            }

            // Make sure user is admin
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }
            //AUTH WORKS SO IF LOGGED IN USER IS ADMIN THEN WONT BE A PROBLEM!!!
            

            ctx.json(principal);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

}
