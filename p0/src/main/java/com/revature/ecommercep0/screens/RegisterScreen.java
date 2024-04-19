package com.revature.ecommercep0.screens;

import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.service.UserService;

public class RegisterScreen extends BaseScreen {
    private final Scanner scan;
    private RouterService routerService;
    private UserService userService;

    public RegisterScreen(Scanner scan, RouterService routerService, UserService userService) {
        this.scan = scan;
        this.routerService = routerService;
        this.userService = userService;
    }

    @Override
    public void startInterface() {
        User newUser = new User();
        
        while (true) {
            clearScreen();
            System.out.println("In Registration Mode...");
            for (User s: userService.findAll()) {
                System.out.println(s.getEmail());
            }
            System.out.println("\nEmail: ");
            String email = scan.nextLine();

            if (!userService.isValidEmail(email)) {
                clearScreen();
                System.out.println("Invalid Email");
                pause(scan);
                continue;
            }

            if (!userService.isUniqueEmail(email)) {
                clearScreen();
                System.out.println("Email is already taken");
                pause(scan);
                continue;
            }
            newUser.setEmail(email);

            while (true) {
                clearScreen();
                System.out.println("Email Accepted");
                System.out.println("\nPassword: ");
                String password = scan.nextLine();
                if (!userService.isValidPassword(password)) {
                    clearScreen();
                    System.out.println("\nPassword must be minimum eight characters, at least one letter and one number");
                    pause(scan);
                    continue;
                }
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12)); //one way, cant be decrypted
                newUser.setPassword(hashedPassword);
                break;
            }

                clearScreen();
                System.out.println("Password Accepted!");
                System.out.println("\nFirst name: ");
                String fname = scan.nextLine();
                newUser.setFname(fname);

                System.out.println("\nLast name: ");
                String lname = scan.nextLine();
                newUser.setLname(lname);
                Role newRole = new Role();
                newUser.setRole_id(newRole.getRole_id());
            // save logic
           userService.save(newUser);

           clearScreen();
           System.out.println("Account created successfully");
           pause(scan);
           routerService.navigate("/start");
            //TODO: Implement userDao.save using Javalin
        }
    }

}
