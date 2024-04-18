package com.revature.ecommercep0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.screens.StartScreen;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.service.UserService;
import com.revature.ecommercep0.utils.ConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args ) throws SQLException, IOException
    {
        Scanner scan = new Scanner(System.in);

        System.out.println(ConnectionFactory.getInstance().getConnection());

        new RouterService(scan, new UserService(new UserDao(), new RoleService(new RoleDao()))).navigate("/start").startInterface();

        scan.close();
    }
}
