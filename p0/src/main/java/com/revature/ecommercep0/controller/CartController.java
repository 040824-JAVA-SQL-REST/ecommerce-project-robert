package com.revature.ecommercep0.controller;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.ecommercep0.dto.request.AddProductToCartRequest;
import com.revature.ecommercep0.dto.response.CartItemResponse;
import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.model.CartHistory;
import com.revature.ecommercep0.service.CartHistoryService;
import com.revature.ecommercep0.service.CartService;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.TokenService;

import io.javalin.http.Context;

public class CartController {
    private final CartHistoryService cartHistoryService;
    private final TokenService tokenService;
    private final CartService cartService;
    // private final ProductService productService;

    public CartController(CartHistoryService cartHistoryService, TokenService tokenService, CartService cartService) {
        this.cartHistoryService = cartHistoryService;
        this.tokenService = tokenService;
        this.cartService = cartService;
    }

    public void getAllProductsInCart(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            // Get token from the header
            String token = ctx.header("auth-token");

            // Validate token
            if (token == null || token.isEmpty()) {
                ctx.status(401); // Unauthorized
                errors.put("Error: ", "Your token is not valid");
                ctx.json(errors);
                return;
            }

            // Now we parse the token to get the principal(auth)
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error: ", "Your token is not valid");
                return;
            }

            // Make sure user is admin
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")
                    && !principal.getRole().getName().equalsIgnoreCase("USER")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }

            Cart myCart = cartService.getActiveCartFromUser(principal.getId());

            // Check if User has active Cart.
            if (myCart == null) {
                ctx.status(400); // Bad Request
                errors.put("Error: ", "User does not have active cart, please add product to cart.");
                ctx.json(errors);
                return;
            }

            List<CartItemResponse> allItemsInCart = cartHistoryService.viewCart(myCart.getId());

            ctx.status(200).json(allItemsInCart);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }

    }

    public void addProductToCart(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            // Get token from the header
            String token = ctx.header("auth-token");

            // Validate token
            if (token == null || token.isEmpty()) {
                ctx.status(401); // Unauthorized
                errors.put("Error: ", "Your token is not valid");
                ctx.json(errors);
                return;
            }

            // Now we parse the token to get the principal(auth)
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error: ", "Your token is not valid");
                return;
            }

            // Make sure user is admin
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")
                    && !principal.getRole().getName().equalsIgnoreCase("USER")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }

            Cart myCart = cartService.getActiveCartFromUser(principal.getId());

            if (myCart == null) {
                myCart = new Cart(myCart.getId());
                cartService.createNewCart(myCart);
            }

            AddProductToCartRequest addProductToCartRequest = ctx.bodyAsClass(AddProductToCartRequest.class);

            // Make sure product(id) exists.
            if (cartHistoryService.getProductService()
                    .findProductById(addProductToCartRequest.getProductId()) == null) {
                ctx.status(400); // Bad Request
                errors.put("Error: ", "Product doesn't exist.");
                ctx.json(errors);
                return;
            }

            // Make sure cart doesnt contain product.
            if (cartHistoryService.containsProduct(addProductToCartRequest.getProductId(), myCart.getId())) {
                ctx.status(400); // Bad Request
                errors.put("Error: ", "Product already exists in cart.");
                ctx.json(errors);
                return;
            }

            CartHistory ch = cartHistoryService.addToCart(myCart.getId(), addProductToCartRequest.getProductId(),
                    addProductToCartRequest.getQuantity());

            ctx.status(200).json(ch);

            // Have to work on it because i realized the steps hes on now doesnt apply to
            // CartController aka being an admin has nothign to do with role
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

}
