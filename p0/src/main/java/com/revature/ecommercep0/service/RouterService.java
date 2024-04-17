package com.revature.ecommercep0.service;

import java.util.Scanner;

import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.screens.BaseScreen;
import com.revature.ecommercep0.screens.RegisterScreen;
import com.revature.ecommercep0.screens.StartScreen;

public class RouterService {
    private final Scanner scan;
    private final UserService userService;
 
    public RouterService(Scanner scan, UserService userService) {
        this.scan = scan;
        this.userService = userService;
    }

    public BaseScreen navigate(String path) {
        switch (path) {
            case "/start":
                return new StartScreen(scan, this);
            case "/register":
                return new RegisterScreen(scan, this, userService);    
        }
        return null;
    }
}
