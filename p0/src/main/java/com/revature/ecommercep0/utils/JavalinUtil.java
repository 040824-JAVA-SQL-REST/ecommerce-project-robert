package com.revature.ecommercep0.utils;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;

import com.revature.ecommercep0.controller.CartController;
import com.revature.ecommercep0.controller.OrderController;
import com.revature.ecommercep0.controller.ProductController;
import com.revature.ecommercep0.controller.UserController;
import com.revature.ecommercep0.dao.CartDao;
import com.revature.ecommercep0.dao.CartHistoryDao;
import com.revature.ecommercep0.dao.OrderDao;
import com.revature.ecommercep0.dao.OrderHistoryDao;
import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.service.CartHistoryService;
import com.revature.ecommercep0.service.CartService;
import com.revature.ecommercep0.service.OrderHistoryService;
import com.revature.ecommercep0.service.OrderService;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.TokenService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.Javalin;

public class JavalinUtil {

    public Javalin getJavalin() throws IOException {
        // Controllers
        UserController userController = new UserController(getUserService(), new TokenService());
        CartController cartController = new CartController(getCartHistoryService(), new TokenService(), new CartService(new CartDao()));
        ProductController productController = new ProductController(new ProductService(new ProductDao()), new TokenService());
        OrderController orderController = new OrderController(new OrderService(new OrderDao()), new OrderHistoryService(new OrderService(new OrderDao()), new ProductService(new ProductDao()), new OrderHistoryDao()), new TokenService());
        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("users/", () -> {
                    post("register/", userController::register);
                    post("login/", userController::login);
                });
                
                path("/cart", () -> {
                    post(cartController::addProductToCart);
                    get(cartController::getAllProductsInCart);
                    post("/update-quantity", cartController::updateCartProductQuantity);
                    post("/checkout", cartController::checkout);
                });
                path("/products", () -> {
                    post(productController::addProductToCatalog);
                    get(productController::getAllProducts);

                });
                path("/products/{productId}", () -> {
                    put(productController::updateProduct);
                    post(productController::deleteProduct);
                });
                path("/orders", () -> {
                    get("/my-order-history", orderController::getOrders);
                    get("/all", orderController::getAllOrders);
                   // post(productController::deleteProduct);
                });

            });
        });
    }
    private UserService getUserService() {
        return new UserService(
                new UserDao(),
                new RoleService(new RoleDao()));
    }
    private CartHistoryService getCartHistoryService() {
        return new CartHistoryService(new CartHistoryDao(), new CartService(new CartDao()), new ProductService(new ProductDao()), new OrderHistoryService(new OrderService(new OrderDao()), new ProductService(new ProductDao()), new OrderHistoryDao()));
    }

}
