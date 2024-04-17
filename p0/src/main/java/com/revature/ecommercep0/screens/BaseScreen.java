package com.revature.ecommercep0.screens;

import java.util.Scanner;

public abstract class BaseScreen {
    //Make contract method, do not have logic, the child will.
    public abstract void startInterface();
    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    protected void pause(Scanner scan) {
        System.out.println("Press enter to continue...");
        scan.nextLine();
    }
}
