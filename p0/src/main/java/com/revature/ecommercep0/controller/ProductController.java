package com.revature.ecommercep0.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.ecommercep0.dto.request.NewProductRequest;
import com.revature.ecommercep0.dto.request.ProductUpdateRequest;
import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.dto.response.ProductCatalogResponse;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.TokenService;

import io.javalin.http.Context;

public class ProductController {
    private final ProductService productService;
    private final TokenService tokenService;

    public ProductController(ProductService productService, TokenService tokenService) {
        this.productService = productService;
        this.tokenService = tokenService;
    }

    public void addProductToCatalog(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            String token = ctx.header("auth-token");

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
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }

            // AUTH WORKS SO IF LOGGED IN USER IS ADMIN THEN WONT BE A PROBLEM!!!
            NewProductRequest req = ctx.bodyAsClass(NewProductRequest.class);
            if (req.getName().isEmpty()) {
                ctx.status(400);
                errors.put("Error: ", "Product name cannot be empty.");
                return;
            }
            // This method is going to account for if its a "deleted" product
            if (!productService.isUniqueProductName(req.getName())) {
                ctx.status(409);
                errors.put("Error: ", "Product name needs to be unique!");
                ctx.json(errors);
            }

            Product producAdded = productService.enterNewProductIntoCatalog(req.getName(), req.getDescription(),
                    req.getPrice(),
                    req.getCategory()); // If it already exists, it just sets isAvailable to true;
            ctx.status(201);

            ctx.json(producAdded);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getAllProducts(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();

            String token = ctx.header("auth-token");
    
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
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN") && !principal.getRole().getName().equalsIgnoreCase("USER")) {
                ctx.status(403); // Forbidden
                errors.put("Error: ", "You do not have access to do this.");
                return;
            }
    
            // Retrieve all AVAILABLE products from the service
            List<Product> products = productService.getAllAvailableProductsCatalog();
            List<ProductCatalogResponse> productCatalog = new ArrayList<>();

            for (Product product: products) {
                ProductCatalogResponse productCatalogResponse = new ProductCatalogResponse(product.getName(), product.getDescription(), product.getPrice(), product.getCategory());
                productCatalog.add(productCatalogResponse);
            }

            // return the list of products as JSON
            ctx.status(200).json(productCatalog);
        } catch (Exception e) {
            ctx.status(500).json("An error occurred while retrieving products");
        }
    }

    public void updateProduct(Context ctx) {
        Map<String, String> errors = new HashMap<>();

        String token = ctx.header("auth-token");

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
        if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
            ctx.status(403); // Forbidden
            errors.put("Error: ", "You do not have access to do this.");
            return;
        }

        // Get product ID from request parameter
        String productId = ctx.pathParam("productId"); // Assuming product ID is in the path parameter

        // Check if product ID is valid
        if (productId == null || productId.isEmpty()) {
            ctx.status(400); // Bad Request
            errors.put("Error: ", "Product ID cannot be empty.");
            ctx.json(errors);
            return;
        }
        // Get updated product details from request body
        ProductUpdateRequest updateRequest = ctx.bodyAsClass(ProductUpdateRequest.class);

        // Call ProductService to update the product
        productService.updateProduct(productId, updateRequest);

        ctx.status(200); // OK

    }

    public void deleteProduct(Context ctx) {
        Map<String, String> errors = new HashMap<>();

        // Check for valid token
        String token = ctx.header("auth-token");
        if (token == null || token.isEmpty()) {
            ctx.status(401); // Unauthorized
            errors.put("Error: ", "Your token is not valid");
            ctx.json(errors);
            return;
        }

        // Parse token to get principal
        Principal principal = tokenService.parseToken(token);
        if (principal == null) {
            ctx.status(401); // Unauthorized
            errors.put("Error: ", "Your token is not valid");
            ctx.json(errors);
            return;
        }

        // Ensure user is admin
        if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
            ctx.status(403); // Forbidden
            errors.put("Error: ", "You do not have access to do this.");
            ctx.json(errors);
            return;
        }

        // Retrieve product id from path parameter
        String productId = ctx.pathParam("productId");

        // Delete product using ProductService
        boolean success = productService.deleteProductFromCatalog(productId);

        if (success) {
            ctx.status(204); // No Content
        } else {
            ctx.status(404); // Not Found
            errors.put("Error: ", "Product not found");
            ctx.json(errors);
        }
    }

}
