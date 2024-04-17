package com.revature.ecommercep0.screens;

import java.util.Scanner;

import com.revature.ecommercep0.service.RouterService;

public class RegisterScreen extends BaseScreen {
    private final Scanner scan;
    private RouterService routerService;

    public RegisterScreen(Scanner scan, RouterService routerService) {
        this.scan = scan;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        while (true) {
            clearScreen();
            System.out.println("In Registration Mode...");

            System.out.println("\nUsername: ");
            String username = scan.nextLine();

            System.out.println("\nPassword: ");
            String password = scan.nextLine();
        }
    }

}
