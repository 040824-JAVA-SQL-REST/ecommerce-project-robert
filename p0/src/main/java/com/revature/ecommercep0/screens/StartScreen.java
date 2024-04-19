package com.revature.ecommercep0.screens;

import java.util.Scanner;

import com.revature.ecommercep0.service.RouterService;

public class StartScreen extends BaseScreen {
    private final Scanner scan;
    private RouterService routerService;

    public StartScreen(Scanner scan, RouterService routerService) {
        this.scan = scan;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        while (true) {
            System.out.println("Welcome to Robs eGrocery Store");
            System.out.println("\n[1] Login");
            System.out.println("\n[2] Register");
            System.out.println("\n[x] Exit");

            System.out.println("\nEnter: ");
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    routerService.navigate("/login").startInterface();
                    break;
                case "2":
                    routerService.navigate("/register").startInterface();;
                    break;
                case "x":
                    return;
                default:
                    clearScreen();
                    System.out.println("Invalid input. Try again!");
                    pause(scan);
                    break;

            }
        }

    }
}
