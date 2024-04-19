package com.revature.ecommercep0.screens;

import java.util.Scanner;

import com.revature.ecommercep0.model.User;

public class HomeScreen extends BaseScreen{
    private User session;
    private final Scanner scan;

    public HomeScreen(User session, Scanner scan) {
        this.session = session;
        this.scan = scan;
    }



    @Override
    public void startInterface() {
        clearScreen();
        System.out.println("Welcome " + session.getFname() + "!");
        pause(scan);
   
    }
    
}
