package com.revature.ecommercep0.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.dto.response.UserOrderItemResponse;
import com.revature.ecommercep0.service.OrderHistoryService;
import com.revature.ecommercep0.service.OrderService;
import com.revature.ecommercep0.service.TokenService;

import io.javalin.http.Context;

public class OrderController {
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService; 
    private final TokenService tokenService;

    public OrderController(OrderService orderService, OrderHistoryService orderHistoryService,
            TokenService tokenService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.tokenService = tokenService;
    }
    public void getOrders(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            // Get token from the header
            String token = ctx.header("auth-token");

            // Validate token
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
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")
                    && !principal.getRole().getName().equalsIgnoreCase("USER")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }

            List<UserOrderItemResponse> allOrdersByUsers = orderHistoryService.allOrdersByUser(principal.getId());
            
            ctx.status(200).json(allOrdersByUsers);


        } catch (Exception e) {
           ctx.status(500);
           e.printStackTrace();
        }
    }

    

    
}
