package com.revature.ecommercep0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.ecommercep0.controller.UserController;
import com.revature.ecommercep0.dao.CartDao;
import com.revature.ecommercep0.dao.OrderDao;
import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.screens.StartScreen;
import com.revature.ecommercep0.service.CartService;
import com.revature.ecommercep0.service.OrderService;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.service.UserService;
import com.revature.ecommercep0.utils.ConnectionFactory;
import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args ) throws SQLException, IOException
    {
        App app = new App();
        UserController userController = new UserController();
        Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("users/",() -> {
                    post("register/", userController::register);
                }); 
            });
        }).start(8080);
        Scanner scan = new Scanner(System.in);
        User session = new User();

        System.out.println(ConnectionFactory.getInstance().getConnection());

        new RouterService(scan, new App().getUserService(), session, new App().getProductService(), new App().getOrderService(), new CartService(new CartDao()) ).navigate("/start").startInterface();

        scan.close();
    }
    private OrderService getOrderService() {
        return new OrderService(new OrderDao());
    }
    private UserService getUserService() {
        return  new UserService(new UserDao(), new RoleService(new RoleDao()));
    }
    private ProductService getProductService() {
        return new ProductService(new ProductDao());
    }
}
