package com.revature.ecommercep0.utils;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;

import com.revature.ecommercep0.controller.UserController;
import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.TokenService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.Javalin;

public class JavalinUtil {

    public Javalin getJavalin() throws IOException {
        // Controllers
        UserController userController = new UserController(getUserService(), new TokenService());

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("users/", () -> {
                    post("register/", userController::register);
                    post("login/", userController::login);
                });
            });
        });
    }
    private UserService getUserService() {
        return new UserService(
                new UserDao(),
                new RoleService(new RoleDao()));
    }

}
