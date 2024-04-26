package com.revature.ecommercep0.controller;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;

import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.service.CartHistoryService;
import com.revature.ecommercep0.service.TokenService;

import io.javalin.http.Context;

public class CartController {
    private final CartHistoryService cartHistoryService;
    private final TokenService tokenService;

    public CartController(CartHistoryService cartHistoryService, TokenService tokenService) {
        this.cartHistoryService = cartHistoryService;
        this.tokenService = tokenService;
    }

    public void addCart(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            //Get token from the header
            String token = ctx.header("auth-token");

                //Validate token
            if (token == null || token.isEmpty()) {
                ctx.status(401); //Unauthorized
                errors.put("Error: ", "Your token is not valid");
                ctx.json(errors);
                return;
            }

            //Now we parse the token to get the principal(auth)
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error: ", "Your token is not valid");
                return;
            }

            //Make sure user is admin
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }

            ctx.json(principal);

            //Have to work on it because i realized the steps hes on now doesnt apply to CartController aka being an admin has nothign to do with role            
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

}
