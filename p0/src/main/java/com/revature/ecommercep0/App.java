package com.revature.ecommercep0;

import java.util.Scanner;

import com.revature.ecommercep0.screens.StartScreen;
import com.revature.ecommercep0.service.RouterService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scan = new Scanner(System.in);
        new RouterService(scan).navigate("/start").startInterface();

        scan.close();
    }
}
