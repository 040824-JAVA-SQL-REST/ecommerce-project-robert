package com.revature.ecommercep0;

import java.util.Scanner;

import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.screens.StartScreen;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.service.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scan = new Scanner(System.in);
        UserDao userDao = new UserDao();
        new RouterService(scan, new UserService(scan, userDao)).navigate("/start").startInterface();

        scan.close();
    }
}
