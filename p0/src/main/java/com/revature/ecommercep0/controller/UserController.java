package com.revature.ecommercep0.controller;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.dto.request.NewLoginRequest;
import com.revature.ecommercep0.dto.request.NewRegisterRequest;
import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.TokenService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.http.Context;

public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void register(Context ctx) {
        ctx.result("User registered");
        try {
            Map<String, String> errors = new HashMap<>();
            // First we have to map postman request to our request class
            NewRegisterRequest req = ctx.bodyAsClass(NewRegisterRequest.class);

            // Now we validate the request
            if (!userService.isValidEmail(req.getEmail())) {
                ctx.status(400); // Bad Request
                return;
            }
            if (!userService.isUniqueEmail(req.getEmail())) {
                ctx.status(409); // Conflcit
                errors.put("Error:  ", "Incorrect Email");
                ctx.json(errors);
                return;
            }
            if (!userService.isValidPassword(req.getPassword())) {
                ctx.status(400);
                return;
            }

            // If "credentials" are valid we save the user;
            User newUser = new User(req);
            newUser = userService.save(newUser);
            if (newUser != null) {
                ctx.status(201); // Created
            }

            ctx.json(req);
        } catch (Exception e) {
            ctx.status(500); // 500 means internal error means not the clients fault
            e.printStackTrace();

        }
    }

    public void login(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            NewLoginRequest req = ctx.bodyAsClass(NewLoginRequest.class);
            Optional<User> loggedUser = userService.login(req.getEmail(), req.getPassword());

            if (loggedUser.isEmpty()) {
                errors.put("Error: ", "Invalid email or password");
                ctx.json(errors);
                ctx.status(401);
                return;
            }
            Principal principal = new Principal(loggedUser.get());

            String token = tokenService.generateToken(principal);

            ctx.header("auth-token", token);

            ctx.json(principal);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }

    }
}
