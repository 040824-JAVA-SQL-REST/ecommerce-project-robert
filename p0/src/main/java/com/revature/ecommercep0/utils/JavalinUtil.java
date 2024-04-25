package com.revature.ecommercep0.utils;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.revature.ecommercep0.controller.UserController;
import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.Javalin;

public class JavalinUtil {

    public Javalin getJavalin() {
        // Controllers
        UserController userController = new UserController(getUserService());

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
