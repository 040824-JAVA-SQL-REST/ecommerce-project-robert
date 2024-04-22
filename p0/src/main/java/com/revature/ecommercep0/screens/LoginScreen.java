package com.revature.ecommercep0.screens;

import java.util.Optional;
import java.util.Scanner;

import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.service.UserService;

public class LoginScreen extends BaseScreen {
    private final Scanner scan;
    private User session;
    private final UserService userService;
    private final RouterService routerService;

    public LoginScreen(Scanner scan, User session, UserService userService, RouterService routerService) {
        this.scan = scan;
        this.session = session;
        this.userService = userService;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        while (true) {
            System.out.println("Now logging in...");

            System.out.println("\nPlease enter email: ");
            String email = scan.nextLine();

            System.out.println("\nPlease enter password: ");
            String password = scan.nextLine();

            Optional<User> optUser = userService.login(email, password);
            if (optUser.isEmpty()) {
                clearScreen();
                System.out.println("Invalid email or password");
                pause(scan);
            } else {
                User temp = optUser.get();
                session.setEmail(temp.getEmail());
                session.setPassword(temp.getPassword());
                session.setFname(temp.getFname());
                session.setLname(temp.getLname());
                session.setRole_id(temp.getRole_id());

                if (userService.isAdmin(temp)) {
                  routerService.navigate("/admin").startInterface();
                }
                  routerService.navigate("/home").startInterface();
                    break; // might not need "else"
            }

        }

    }

}
