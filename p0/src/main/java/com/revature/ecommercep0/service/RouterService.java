package com.revature.ecommercep0.service;

import java.util.Scanner;

import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.screens.AdminScreen;
import com.revature.ecommercep0.screens.BaseScreen;
import com.revature.ecommercep0.screens.HomeScreen;
import com.revature.ecommercep0.screens.LoginScreen;
import com.revature.ecommercep0.screens.RegisterScreen;
import com.revature.ecommercep0.screens.StartScreen;

public class RouterService {
    private final Scanner scan;
    private  UserService userService;
    private User session;
    private ProductService productService;
    private OrderService orderService;
    private CartService cartService;

    public RouterService(Scanner scan, UserService userService, User session, ProductService productService, OrderService orderService, CartService cartService) {
        this.scan = scan;
        this.userService = userService;
        this.session = session;
        this.productService = productService;
        this.orderService = orderService;
        this.cartService = cartService;

    }

    public BaseScreen navigate(String path) {
        switch (path) {
            case "/start":
                return new StartScreen(scan, this);
            case "/register":
                return new RegisterScreen(scan, this, userService);    
            case "/login":
                return new LoginScreen(scan, session, userService, this);
            case "/home":
                return new HomeScreen(session, scan, productService);
            case "/admin":
                return new AdminScreen(scan, session, productService, this, orderService, cartService);
        }
        return null;
    }
}
